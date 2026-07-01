package com.personal.live_match.modules.match.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.live_match.modules.match.business.entities.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {}
