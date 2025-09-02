package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.EmailContact;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EmailContactRepository extends BaseRepository<EmailContact, UUID> {
}