package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.Consent;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends BaseRepository<Consent, Long> {
}