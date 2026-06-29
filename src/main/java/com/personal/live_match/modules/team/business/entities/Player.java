package com.personal.live_match.modules.team.business.entities;

import java.util.Set;

import com.personal.live_match.modules.match.business.entities.LineupPlayer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "players")
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    private String notes;

    @OneToMany(mappedBy = "player")
    private Set<TeamPlayer> teamPlayers;

    @OneToMany(mappedBy = "player")
    private Set<LineupPlayer> lineupPlayers;

    public Player(String name, String notes) {
        this.name = name;
        this.notes = notes;
    }

    public Player(Integer id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
    }
}
