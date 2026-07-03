package com.personal.live_match.modules.ingestion.plattforms.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.live_match.modules.events.entities.MatchEventMessage;
import com.personal.live_match.modules.ingestion.plattforms.api.payloads.RegisterEventPayload;
import com.personal.live_match.modules.ingestion.plattforms.api.responses.MatchEventResponse;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/ingestion/events")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class IngestionController {
    private final KafkaTemplate<String, MatchEventMessage> kafkaTemplate;

    @PostMapping
    public ResponseEntity<MatchEventResponse> registerEvent(@RequestBody RegisterEventPayload payload) {
        Map<String, Object> map = new HashMap<>();

        var eventType =  MatchEventMessage.EventType.valueOf(payload.eventType());

        var matchEventMessage = new MatchEventMessage(
            payload.eventId(),
            payload.matchId(),
            eventType,
            payload.position(),
            payload.minute(),
            map
        );

        kafkaTemplate.send("match_events", payload.matchId().toString(), matchEventMessage);

        var response = new MatchEventResponse("ok", "match event published!");

        return ResponseEntity.status(201).body(response);
    }
}
