package com.personal.live_match.modules.match.business.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    private String description;

    @OneToMany(mappedBy = "event")
    private Set<MatchEvent> matchEvents;

    public Event(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Event(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
