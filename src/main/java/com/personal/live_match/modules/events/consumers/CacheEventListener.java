package com.personal.live_match.modules.events.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.personal.live_match.modules.events.entities.MatchEventMessage;
import com.personal.live_match.modules.shared.services.MatchState;
import com.personal.live_match.modules.shared.services.MatchStateCacheService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CacheEventListener {
    private final MatchStateCacheService cacheService;

    @KafkaListener(
        topics = "match_events",
        groupId = "cache",
        containerFactory = "cacheContainerFactory"
    )
    public void consume(MatchEventMessage event) {
        Integer matchId = event.getMatchId();

        MatchState currentState = cacheService.getMatchState(matchId)
            .orElseGet(() -> new MatchState(0, 0, "NOT_STARTED", "0"));

        MatchState updatedState = applyEvent(currentState, event);

        cacheService.saveMatchState(matchId, updatedState);
    }

    private MatchState applyEvent(MatchState state, MatchEventMessage event) {
        switch (event.getType()) {
            case GOAL_HOME:
                return new MatchState(
                    state.getHomeScore() + 1,
                    state.getAwayScore(),
                    "LIVE",
                    event.getEventMinute()
                );

            case GOAL_AWAY:
                return new MatchState(
                    state.getHomeScore(),
                    state.getAwayScore() + 1,
                    "LIVE",
                    event.getEventMinute()
                );

            case MATCH_START:
                return new MatchState(0, 0, "LIVE", "0");

            case MATCH_END:
                return new MatchState(
                    state.getHomeScore(),
                    state.getAwayScore(),
                    "FINISHED",
                    event.getEventMinute()
                );

            case MINUTE_UPDATE:
                return new MatchState(
                    state.getHomeScore(),
                    state.getAwayScore(),
                    state.getStatus(),
                    event.getEventMinute()
                );

            default:
                return state;
        }
    }
}