package com.personal.live_match.modules.match.business.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "matches")
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String title;
    private String notes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @OneToMany(mappedBy = "match")
    private Set<MatchEvent> matchEvents;

    @OneToMany(mappedBy = "match")
    private Set<MatchTeam> matchTeams;

    public Match(String title, String notes, Stage stage) {
        this.title = title;
        this.notes = notes;
        this.stage = stage;
    }

    public Match(Integer id, String title, String notes, Stage stage) {
        this.title = title;
        this.notes = notes;
        this.stage = stage;
    }
}
