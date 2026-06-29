package com.personal.live_match.modules.match.business.entities;

import java.util.Set;

import com.personal.live_match.modules.team.business.entities.Team;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "competitions")
@NoArgsConstructor
@Getter
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String title;
    private String description;

    @ManyToMany
    @JoinTable(
        name = "competition_teams",
        joinColumns = @JoinColumn(name = "competition_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams;

    @OneToMany(mappedBy = "competition")
    private Set<Stage> stages;

    public Competition(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Competition(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
