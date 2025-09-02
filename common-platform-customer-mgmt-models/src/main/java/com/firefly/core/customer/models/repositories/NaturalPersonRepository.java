package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.NaturalPerson;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Repository
public interface NaturalPersonRepository extends BaseRepository<NaturalPerson, UUID> {
    
    /**
     * Finds all natural persons associated with a specific party.
     *
     * @param partyId the unique identifier of the party
     * @return a Flux of NaturalPerson entities belonging to the specified party
     */
    Flux<NaturalPerson> findByPartyId(UUID partyId);
}