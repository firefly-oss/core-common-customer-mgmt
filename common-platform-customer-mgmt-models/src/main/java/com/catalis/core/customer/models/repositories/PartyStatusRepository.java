package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.PartyStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyStatusRepository extends BaseRepository<PartyStatus, Long> {
}