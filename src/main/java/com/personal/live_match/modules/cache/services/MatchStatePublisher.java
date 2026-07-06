package com.personal.live_match.modules.cache.services;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.live_match.modules.stream.entities.MatchState;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchStatePublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @SuppressWarnings("null")
    public void publishMatchState(Integer matchId, MatchState state) {
        try {
            String json = objectMapper.writeValueAsString(state);
            redisTemplate.convertAndSend("match:" + matchId + ":state", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize MatchState for match " + matchId, e);
        }
    }
}
