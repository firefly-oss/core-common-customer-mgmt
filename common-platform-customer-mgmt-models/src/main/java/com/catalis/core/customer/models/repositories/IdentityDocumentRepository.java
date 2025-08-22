package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.IdentityDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityDocumentRepository extends BaseRepository<IdentityDocument, Long> {
}