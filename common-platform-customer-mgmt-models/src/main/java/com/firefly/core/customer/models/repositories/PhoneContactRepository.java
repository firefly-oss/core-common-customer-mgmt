package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.PhoneContact;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PhoneContactRepository extends BaseRepository<PhoneContact, UUID> {
}