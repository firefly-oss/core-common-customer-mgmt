package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.PartyRelationship;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PartyRelationshipRepository extends BaseRepository<PartyRelationship, UUID> {
}