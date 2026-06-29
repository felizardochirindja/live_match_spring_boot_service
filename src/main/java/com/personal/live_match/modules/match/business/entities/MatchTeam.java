package com.personal.live_match.modules.match.business.entities;

import com.personal.live_match.modules.team.business.entities.Team;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "match_teams")
@NoArgsConstructor
public class MatchTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private Boolean principal;

    public MatchTeam(Match match, Team team, Boolean principal) {
        this.match = match;
        this.team = team;
        this.principal = principal;
    }

    public MatchTeam(Integer id, Match match, Team team, Boolean principal) {
        this.id = id;
        this.match = match;
        this.team = team;
        this.principal = principal;
    }
}
