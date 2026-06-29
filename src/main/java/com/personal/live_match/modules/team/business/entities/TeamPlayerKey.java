package com.personal.live_match.modules.team.business.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class TeamPlayerKey implements Serializable {
    @Column(name = "team_id", columnDefinition = "CHAR(4)", insertable = false, updatable = false)
    private String teamId;
    
    @Column(name = "player_id", insertable = false, updatable = false)
    private Integer playerId;
}
