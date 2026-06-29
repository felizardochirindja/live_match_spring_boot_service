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

@Entity
@Table(name = "stages")
@NoArgsConstructor
@Getter
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "competition_id")
    private Competition competition;
    
    @OneToMany(mappedBy = "stage")
    private Set<Match> matches;

    public Stage(String name, String description, Competition competition) {
        this.name = name;
        this.description = description;
        this.competition = competition;
    }

    public Stage(Integer id, String name, String description, Competition competition) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.competition = competition;
    }
}
