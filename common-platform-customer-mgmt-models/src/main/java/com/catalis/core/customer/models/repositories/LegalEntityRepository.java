package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.LegalEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface LegalEntityRepository extends BaseRepository<LegalEntity, Long> {
    
    /**
     * Finds all legal entities associated with a specific party.
     *
     * @param partyId the unique identifier of the party
     * @return a Flux of LegalEntity entities belonging to the specified party
     */
    Flux<LegalEntity> findByPartyId(Long partyId);
}