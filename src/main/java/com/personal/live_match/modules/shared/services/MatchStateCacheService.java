package com.personal.live_match.modules.shared.services;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class MatchStateCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveMatchState(Integer matchId, MatchState state) {
        String key = generateKey(matchId);

        Map<String, String> fields = Map.of(
            "homeScore", state.getHomeScore().toString(),
            "awayScore", state.getAwayScore().toString(),
            "status", state.getStatus(),
            "minute", state.getMinute()
        );

        redisTemplate.opsForHash().putAll(key, fields);
        redisTemplate.expire(key, Duration.ofMinutes(30));
    }

    public void updateFields(Integer matchId, Map<String, String> fields) {
        String key = generateKey(matchId);
        redisTemplate.opsForHash().putAll(key, fields);
        redisTemplate.expire(key, Duration.ofMinutes(30));
    }

    public void updateField(Integer matchId, String field, String value) {
        redisTemplate.opsForHash().put(generateKey(matchId), field, value);
    }

    public Optional<MatchState> getMatchState(Integer matchId) {
        Map<Object, Object> fields = redisTemplate.opsForHash().entries(generateKey(matchId));
        if (fields.isEmpty()) return Optional.empty();

        return Optional.of(new MatchState(
            Integer.parseInt(fields.get("homeScore").toString()),
            Integer.parseInt(fields.get("awayScore").toString()),
            (String) fields.get("status"),
            (String) fields.get("minute")
        ));
    }

    private String generateKey(Integer matchId) {
        return "match:" + matchId + ":state";
    }
}