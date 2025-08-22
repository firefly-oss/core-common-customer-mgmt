package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.Consent;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends BaseRepository<Consent, Long> {
}