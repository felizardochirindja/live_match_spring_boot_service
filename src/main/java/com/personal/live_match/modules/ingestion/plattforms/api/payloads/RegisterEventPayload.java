package com.personal.live_match.modules.ingestion.plattforms.api.payloads;

public record RegisterEventPayload(
    Integer eventId,
    Integer matchId,
    String eventType,
    Integer position,
    String minute
) {
}
