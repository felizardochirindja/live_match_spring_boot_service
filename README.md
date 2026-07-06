# Xingufo Scoreboard

A real-time live sports match event streaming service built with Spring Boot. It ingests match events via a REST API, processes them through Kafka, persists them to MySQL, caches state in Redis, and broadcasts live updates to clients via Server-Sent Events (SSE).

## Architecture

```
Client POST /api/ingestion/events
       │
       ▼
 IngestionController ─────► Kafka "match_events" topic
       │                        │
       │          ┌─────────────┴─────────────┐
       │          │                           │
       │   DatabaseEventListener      CacheEventListener
       │   (consumer group: "database") (consumer group: "cache")
       │          │                           │
       │          ▼                           ▼
       │   MatchEventLedgerService    MatchStateCacheService
       │          │                           │
       │          ▼                           ▼
       │        MySQL                  Redis Hash (match:{id}:state)
       │                             MatchStatePublisher
       │                                    │
       │                                    ▼
       │                             Redis Pub/Sub (match:{id}:state)
       │                                    │
       │                                    ▼
       │                             MatchStateSubscriber
       │                                    │
       │                                    ▼
       │                             SSE Emitters
       │                                    │
       ▼                                    ▼
 Client GET /api/matches/{id}/state   Client GET /api/matches/{id}/stream
```

## Tech Stack

| Component     | Technology             |
|---------------|------------------------|
| Runtime       | Java 21                |
| Framework     | Spring Boot 3.5.15     |
| Message Broker| Apache Kafka           |
| Database      | MySQL (Flyway migrations) |
| Cache         | Redis (Hash + Pub/Sub) |
| Real-time     | Server-Sent Events (SSE) |
| API Docs      | SpringDoc OpenAPI (Swagger) |
| Build         | Maven                  |

## Prerequisites

- Java 21+
- MySQL 8+
- Redis 7+
- Apache Kafka (with Zookeeper or KRaft mode)

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd live_match_spring_boot_service
```

### 2. Set up infrastructure

Make sure MySQL, Redis, and Kafka are running on localhost with the default ports:

| Service | Host     | Port |
|---------|----------|------|
| MySQL   | localhost| 3306 |
| Redis   | localhost| 6379 |
| Kafka   | localhost| 9092 |

Create the MySQL database:

```bash
mysql -u root -p -e "CREATE DATABASE live_match_spring_api;"
```

### 3. Configure application properties

Edit `src/main/resources/application.yaml` to match your environment credentials (MySQL username/password, Kafka/Redis hosts).

### 4. Run the application

```bash
./mvnw spring-boot:run
```

The service starts on `http://localhost:8080`.

Flyway will automatically run database migrations on startup.

## API Endpoints

### Ingest a match event

```bash
POST /api/ingestion/events
Content-Type: application/json

{
  "eventId": 1,
  "matchId": 10,
  "eventType": "GOAL_HOME",
  "position": "forward",
  "minute": "45"
}
```

Supported event types: `MATCH_START`, `MATCH_END`, `GOAL_HOME`, `GOAL_AWAY`, `YELLOW_CARD`, `RED_CARD`, `SUBSTITUTION`, `HALF_TIME`, `FULL_TIME`, `VAR_REVIEW`, `MINUTE_UPDATE`

### Get current match state (snapshot)

```bash
GET /api/matches/{id}/state
```

Returns the cached match state from Redis:

```json
{
  "status": "IN_PROGRESS",
  "message": "Match state retrieved",
  "data": {
    "homeScore": 2,
    "awayScore": 1,
    "status": "IN_PROGRESS",
    "minute": "65"
  }
}
```

### Stream live match updates (SSE)

```bash
GET /api/matches/{id}/stream
Accept: text/event-stream
```

Returns an SSE stream that pushes `match-state` events whenever the match state changes.

### API Documentation

- Swagger UI: `http://localhost:8080/swagger-docs`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Project Structure

```
src/main/java/com/personal/live_match/
└── modules/
    ├── ingestion/          # REST API for event ingestion → Kafka
    │   └── plattforms/api/
    │       ├── IngestionController
    │       ├── payloads/RegisterEventPayload
    │       └── responses/MatchEventResponse
    ├── events/             # Kafka configuration & consumers
    │   ├── configs/KafkaConfig
    │   ├── entities/MatchEventMessage, EventType
    │   └── consumers/DatabaseEventListener, CacheEventListener
    ├── cache/              # Redis cache & Pub/Sub
    │   ├── configs/RedisConfig
    │   └── services/
    │       ├── MatchStateCacheService
    │       ├── MatchStatePublisher
    │       ├── MatchStateSubscriber
    ├── stream/             # SSE live stream API
    │   ├── entities/MatchState
    │   └── platoforms/api/
    │       ├── LiveStreamController
    │       └── responses/MatchStateResponse
    ├── match/              # Match domain (JPA entities, repositories, service)
    │   ├── entities/Event, Match, MatchEvent, MatchEventKey, ...
    │   ├── repositories/MatchRepository, MatchEventRepository, EventRepository
    │   └── services/MatchEventLedgerService
    └── team/               # Team domain (JPA entities)
        └── entities/Team, Player, TeamPlayer, TeamPlayerKey
```

## Database Schema

Flyway manages all schema migrations under `src/main/resources/db/migration/`:

| Migration | Tables Created |
|-----------|----------------|
| V1 | `events` |
| V2 | `players` |
| V3 | `teams`, `team_players` |
| V4 | `competitions`, `competition_teams` |
| V5 | `stages` |
| V6 | `matches`, `match_teams`, `match_events` |
| V7 | `lineups`, `lineup_players` |

Key features:
- `match_events.payload` is a JSON column for flexible event data
- Team IDs are `CHAR(4)` codes (e.g., `"FCB"`)
- All tables use InnoDB with `utf8mb4` charset
- JPA `ddl-auto` is set to `validate` — schema changes must go through Flyway

## Data Flow

1. **Ingestion** — A client posts a match event to `/api/ingestion/events`
2. **Kafka publish** — The controller publishes a `MatchEventMessage` to the `match_events` Kafka topic
3. **Database consumer** — The `database` consumer group persists the event to MySQL via `MatchEventLedgerService`
4. **Cache consumer** — The `cache` consumer group:
   - Reads the current `MatchState` from Redis (or initializes a new one)
   - Applies the event transformation (e.g., increment score on `GOAL_HOME`, set status on `MATCH_END`)
   - Saves the updated state back to Redis (30-min TTL)
   - Publishes the new state via Redis Pub/Sub
5. **SSE broadcast** — The `MatchStateSubscriber` receives the Pub/Sub message and pushes it to all SSE subscribers for that match
6. **Client receives** — Connected clients receive real-time `match-state` events via SSE

## Running Tests

```bash
./mvnw test
```

Currently only a context-load smoke test (`LiveMatchApplicationTests`) is available.

## License

This project is licensed under the terms found in the [LICENSE](LICENSE) file.
