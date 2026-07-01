package com.personal.live_match.modules.match.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.personal.live_match.modules.match.business.entities.MatchEvent;
import com.personal.live_match.modules.match.business.entities.MatchEventKey;
import java.util.List;

public interface MatchEventRepository extends JpaRepository<MatchEvent, MatchEventKey> {
    List<MatchEvent> findByMatchId(Integer matchId);
}
