package com.personal.live_match.modules.match.business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "match_events")
@NoArgsConstructor
public class MatchEvent {
    @EmbeddedId
    private MatchEventKey id;

    @ManyToOne(optional = false)
    @MapsId("matchId")
    private Match match;

    @ManyToOne(optional = false)
    @MapsId("eventId")
    private Event event;

    private String eventMinute;
    private int position;
    @Column(columnDefinition = "JSON")
    private String payload;

    public MatchEvent(Match match, Event event, String eventMinute, int position, String payload) {
        this.id = new MatchEventKey(match.getId(), event.getId());
        this.match = match;
        this.event = event;
        this.eventMinute = eventMinute;
        this.position = position;
        this.payload = payload;
    }
}
