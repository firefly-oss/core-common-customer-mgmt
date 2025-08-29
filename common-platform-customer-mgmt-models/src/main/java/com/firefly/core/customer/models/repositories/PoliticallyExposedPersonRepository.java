package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.PoliticallyExposedPerson;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliticallyExposedPersonRepository extends BaseRepository<PoliticallyExposedPerson, Long> {
}