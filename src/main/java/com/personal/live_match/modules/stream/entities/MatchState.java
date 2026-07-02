package com.personal.live_match.modules.stream.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MatchState {
    Integer homeScore;
    Integer awayScore;
    String status;
    String minute;
}
