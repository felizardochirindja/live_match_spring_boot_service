package com.personal.live_match.modules.stream.platoforms.api.responses;

import com.personal.live_match.modules.stream.entities.MatchState;

public record MatchStateResponse(
    String status,
    String message,
    MatchState data
) {
}
