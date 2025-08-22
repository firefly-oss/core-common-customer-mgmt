package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.NaturalPerson;
import org.springframework.stereotype.Repository;

@Repository
public interface NaturalPersonRepository extends BaseRepository<NaturalPerson, Long> {
}