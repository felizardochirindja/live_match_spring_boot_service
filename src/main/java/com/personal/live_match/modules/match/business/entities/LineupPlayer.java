package com.personal.live_match.modules.match.business.entities;

import com.personal.live_match.modules.team.business.entities.Player;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "lineup_players")
@NoArgsConstructor
public class LineupPlayer {
    @EmbeddedId
    private LineupPlayerKey id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lineup_id", nullable = false)
    @MapsId("lineupId")
    private Lineup lineup;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @MapsId("playerId")
    private Player player;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        GOAL_KEEPER
    }

    public LineupPlayer(Lineup lineup, Player player, Role role) {
        this.lineup = lineup;
        this.player = player;
        this.role = role;
    }
}
