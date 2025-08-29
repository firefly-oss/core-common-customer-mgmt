package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.IdentityDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityDocumentRepository extends BaseRepository<IdentityDocument, Long> {
}