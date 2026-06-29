package com.personal.live_match.modules.match.business.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lineups")
@NoArgsConstructor
@Getter
public class Lineup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Boolean initial;
    private int position;

    @ManyToOne(optional = false)
    private MatchTeam matchTeam;

    @OneToMany(mappedBy = "lineup")
    private Set<LineupPlayer> lineupPlayers;

    public Lineup(Boolean initial, int position, MatchTeam matchTeam) {
        this.initial = initial;
        this.position = position;
        this.matchTeam = matchTeam;
    }
    
    public Lineup(Integer id, Boolean initial, int position, MatchTeam matchTeam) {
        this.id = id;
        this.initial = initial;
        this.position = position;
        this.matchTeam = matchTeam;
    }
}
