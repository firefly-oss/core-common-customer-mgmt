package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.PoliticallyExposedPerson;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliticallyExposedPersonRepository extends BaseRepository<PoliticallyExposedPerson, Long> {
}