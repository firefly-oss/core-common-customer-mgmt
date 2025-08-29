package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.PhoneContact;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneContactRepository extends BaseRepository<PhoneContact, Long> {
}