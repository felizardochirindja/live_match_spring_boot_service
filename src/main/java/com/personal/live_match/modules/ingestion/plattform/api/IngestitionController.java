package com.personal.live_match.modules.ingestion.plattform.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.live_match.modules.events.entities.MatchEventMessage;
import com.personal.live_match.modules.ingestion.plattform.api.payloads.RegisterEventPayload;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/ingestion/events")
@RequiredArgsConstructor
public class IngestitionController {
    private final KafkaTemplate<String, MatchEventMessage> kafkaTemplate;

    @PostMapping
    public String registerEvent(@RequestBody RegisterEventPayload payload) {
        Map<String, Object> map = new HashMap<>();

        var eventType =  MatchEventMessage.EventType.valueOf(payload.eventType());

        var event = new MatchEventMessage(
            payload.eventId(),
            payload.matchId(),
            eventType,
            payload.position(),
            payload.minute(),
            map
        );

        kafkaTemplate.send("match_events", payload.matchId().toString(), event);

        return "i am here!";
    }
}
