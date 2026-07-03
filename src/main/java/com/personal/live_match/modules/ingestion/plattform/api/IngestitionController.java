package com.personal.live_match.modules.ingestion.plattform.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.live_match.modules.events.entities.MatchEventMessage;
import com.personal.live_match.modules.ingestion.plattform.api.payloads.RegisterEventPayload;
import com.personal.live_match.modules.ingestion.plattform.api.responses.MatchEventResponse;
import com.personal.live_match.modules.match.repositories.EventRepository;
import com.personal.live_match.modules.match.repositories.MatchRepository;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/ingestion/events")
@RequiredArgsConstructor
public class IngestitionController {
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
