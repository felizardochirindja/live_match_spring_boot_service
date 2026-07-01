package com.personal.live_match.modules.events.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.live_match.modules.events.entities.MatchEventMessage;
import com.personal.live_match.modules.match.business.services.MatchEventLedgerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseEventListener {
    private final MatchEventLedgerService matchEventLedgerService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
        topics = "match_events",
        groupId = "database",
        containerFactory = "databaseContainerFactory"
    )
    public void consume(MatchEventMessage event) {
        log.info("Persisting match event: {}", event);

        String payload;

        try {
            payload = objectMapper.writeValueAsString(event.getPayload());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event payload", e);
        }

        matchEventLedgerService.addEventToLedger(
            event.getMatchId(),
            event.getEventId(),
            event.getEventMinute(),
            event.getPosition(),
            payload
        );
    }
}
