package com.personal.live_match.modules.team.business.entities;

import java.util.Set;

import com.personal.live_match.modules.match.business.entities.Competition;
import com.personal.live_match.modules.match.business.entities.MatchTeam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "teams")
@NoArgsConstructor
public class Team {
    @Id
    @Column(length = 4, columnDefinition = "CHAR(4)")
    private String id;

    private String name;
    private String notes;
    private String trainer_name;

    @ManyToMany(mappedBy = "teams")
    private Set<Competition> competitions;

    @OneToMany(mappedBy = "team")
    private Set<TeamPlayer> teamPlayers;

    @OneToMany(mappedBy = "team")
    private Set<MatchTeam> matchTeams;

    public Team(String id, String name, String notes, String trainer_name) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.trainer_name = trainer_name;
    }
}
