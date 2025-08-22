package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.LegalEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalEntityRepository extends BaseRepository<LegalEntity, Long> {
}