package com.personal.live_match.modules.events.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.personal.live_match.modules.events.entities.MatchEventMessage;

@Component
public class DatabaseEventListener {
    @KafkaListener(
        topics = "match_events",
        groupId = "database",
        containerFactory = "databaseContainerFactory"
    )
    public void consume(MatchEventMessage event) {
        System.out.println("Persistindo evento no banco de dados: " + event);
    }
}
