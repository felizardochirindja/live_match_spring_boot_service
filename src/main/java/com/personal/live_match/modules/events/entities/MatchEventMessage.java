package com.personal.live_match.modules.events.entities;

import java.time.Instant;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchEventMessage {
    private String eventId;
    private String matchId;
    private EventType type;
    private long position;
    private String eventMinute;
    private Map<String, Object> payload;

    public enum EventType {
        GOAL,
        YELLOW_CARD,
        RED_CARD,
        SUBSTITUTION,
        HALF_TIME,
        FULL_TIME,
        VAR_REVIEW
    }
}
