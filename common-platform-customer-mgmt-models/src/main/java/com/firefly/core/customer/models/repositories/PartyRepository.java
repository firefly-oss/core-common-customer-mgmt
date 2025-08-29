package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.Party;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends BaseRepository<Party, Long> {
}