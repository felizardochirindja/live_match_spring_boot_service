package com.personal.live_match.modules.match.business.services;

import com.personal.live_match.modules.match.business.entities.Event;
import com.personal.live_match.modules.match.business.entities.Match;
import com.personal.live_match.modules.match.business.entities.MatchEvent;
import com.personal.live_match.modules.match.repositories.EventRepository;
import com.personal.live_match.modules.match.repositories.MatchEventRepository;
import com.personal.live_match.modules.match.repositories.MatchRepository;

import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchEventLedgerService {
    private final MatchEventRepository matchEventRepository;
    private final MatchRepository matchRepository;
    private final EventRepository eventRepository;

    public MatchEvent addEventToLedger(
        Integer matchId,
        Integer eventId,
        @NonNull String eventMinute,
        @NonNull int position,
        @NonNull String payload
    ) {
        Objects.requireNonNull(matchId, "matchId cannot be null");
        Objects.requireNonNull(eventId, "eventId cannot be null");

        Match match = matchRepository.findById(matchId)
            .orElseThrow(() -> new IllegalArgumentException("Match not found with id: " + matchId));
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + eventId));

        MatchEvent matchEvent = new MatchEvent(match, event, eventMinute, position, payload);

        return matchEventRepository.save(matchEvent);
    }
}
