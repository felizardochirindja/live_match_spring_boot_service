package com.personal.live_match.modules.match.business.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class LineupPlayerKey implements Serializable {
    @Column(name = "lineup_id", insertable = false, updatable = false)
    private Integer lineupId;
    
    @Column(name = "player_id", insertable = false, updatable = false)
    private Integer playerId;
}
