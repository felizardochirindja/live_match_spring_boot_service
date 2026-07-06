package com.personal.live_match.modules.cache.services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.live_match.modules.stream.entities.MatchState;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchStateSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<Integer, CopyOnWriteArrayList<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(Integer matchId) {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);

        emitters.computeIfAbsent(matchId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> removeEmitter(matchId, emitter));
        emitter.onTimeout(() -> removeEmitter(matchId, emitter));
        emitter.onError(e -> removeEmitter(matchId, emitter));

        return emitter;
    }

    @Override
    @SuppressWarnings("null")
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        Integer matchId = extractMatchId(channel);
        String body = new String(message.getBody());

        try {
            MatchState state = objectMapper.readValue(body, MatchState.class);

            var matchEmitters = emitters.get(matchId);
            if (matchEmitters == null) return;

            for (SseEmitter emitter : matchEmitters) {
                try {
                    emitter.send(SseEmitter.event().name("match-state").data(state));
                } catch (Exception e) {
                    removeEmitter(matchId, emitter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeEmitter(Integer matchId, SseEmitter emitter) {
        var matchEmitters = emitters.get(matchId);
        
        if (matchEmitters != null) {
            matchEmitters.remove(emitter);
            
            if (matchEmitters.isEmpty()) {
                emitters.remove(matchId);
            }
        }
    }

    private Integer extractMatchId(String channel) {
        String[] parts = channel.split(":");
        return Integer.parseInt(parts[1]);
    }
}
