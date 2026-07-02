package com.personal.live_match.modules.stream.platoforms.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.live_match.modules.stream.platoforms.api.responses.MatchStateResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/api/matches")
public class LiveStreamController {
    @GetMapping("/{id}/state")
    public ResponseEntity<MatchStateResponse> getMatchState(@PathVariable Integer id) {
        var response = new MatchStateResponse("success", "Match state retrieved", null);

        return ResponseEntity.ok(response);
    }
}
