package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.PartyStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface PartyStatusRepository extends BaseRepository<PartyStatus, UUID> {
    
    /**
     * Finds a PartyStatus by the partyId.
     *
     * @param partyId the ID of the party
     * @return a Mono containing the PartyStatus if found
     */
    Mono<PartyStatus> findByPartyId(UUID partyId);
}