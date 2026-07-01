package com.personal.live_match.modules.match.business.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MatchEventKey implements Serializable{
    @Column(name = "match_id", insertable = false, updatable = false)
    private Integer matchId;

    @Column(name = "event_id", insertable = false, updatable = false)
    private Integer eventId;
}
