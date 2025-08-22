package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.Party;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends BaseRepository<Party, Long> {
}