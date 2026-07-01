package com.personal.live_match.modules.events.entities;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchEventMessage {
    private Integer eventId;
    private Integer matchId;
    private EventType type;
    private int position;
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
