package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.Consent;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ConsentRepository extends BaseRepository<Consent, UUID> {
}