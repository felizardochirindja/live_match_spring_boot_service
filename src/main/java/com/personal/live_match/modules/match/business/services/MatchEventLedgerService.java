package com.personal.live_match.modules.match.business.services;

import com.personal.live_match.modules.match.business.entities.Event;
import com.personal.live_match.modules.match.business.entities.Match;
import com.personal.live_match.modules.match.business.entities.MatchEvent;
import com.personal.live_match.modules.match.repositories.EventRepository;
import com.personal.live_match.modules.match.repositories.MatchEventRepository;
import com.personal.live_match.modules.match.repositories.MatchRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
        @NonNull Integer matchId,
        @NonNull Integer eventId,
        @NonNull String eventMinute,
        int position,
        @NonNull String payload
    ) {
        Match match = matchRepository.findById(matchId)
            .orElseThrow(() -> new IllegalArgumentException("Match not found with id: " + matchId));
        
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + eventId));

        MatchEvent matchEvent = new MatchEvent(match, event, eventMinute, position, payload);

        return matchEventRepository.save(matchEvent);
    }
}
