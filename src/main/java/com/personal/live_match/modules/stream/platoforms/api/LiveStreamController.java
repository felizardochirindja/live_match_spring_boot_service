package com.personal.live_match.modules.stream.platoforms.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.live_match.modules.cache.services.MatchStateCacheService;
import com.personal.live_match.modules.stream.platoforms.api.responses.MatchStateResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/api/matches")
@RequiredArgsConstructor
public class LiveStreamController {
    private final MatchStateCacheService matchStateCacheService;

    @GetMapping("/{id}/state")
    public ResponseEntity<MatchStateResponse> getMatchState(@PathVariable Integer id) {
        var matchState = matchStateCacheService.getMatchState(id);

        if (matchState.isPresent()) {
            return ResponseEntity.ok(new MatchStateResponse("success", "Match state retrieved", matchState.get()));
        }

        return ResponseEntity.status(404).body(new MatchStateResponse("error", "Match state not found", null));
    }
}
