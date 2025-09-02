package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.PartyProvider;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PartyProviderRepository extends BaseRepository<PartyProvider, UUID> {
}