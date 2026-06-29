package com.personal.live_match.modules.team.business.entities;

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
@Table(name = "team_players")
@NoArgsConstructor
public class TeamPlayer {
    @EmbeddedId
    private TeamPlayerKey id;

    @ManyToOne(optional = false)
    @MapsId("teamId")
    @JoinColumn(name = "team_id", columnDefinition = "CHAR(4)", nullable = false)
    private Team team;

    @ManyToOne(optional = false)
    @MapsId("playerId")
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        GUARDA_REDES
    }

    public TeamPlayer(Team team, Player player, Role role) {
        this.team = team;
        this.player = player;
        this.role = role;
    }
}
