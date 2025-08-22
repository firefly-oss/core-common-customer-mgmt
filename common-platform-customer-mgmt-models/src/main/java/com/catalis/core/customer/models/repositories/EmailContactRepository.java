package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.EmailContact;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailContactRepository extends BaseRepository<EmailContact, Long> {
}