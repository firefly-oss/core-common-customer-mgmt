package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.PoliticallyExposedPerson;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PoliticallyExposedPersonRepository extends BaseRepository<PoliticallyExposedPerson, UUID> {
}