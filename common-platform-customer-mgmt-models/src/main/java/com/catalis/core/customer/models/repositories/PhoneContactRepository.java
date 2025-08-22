package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.PhoneContact;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneContactRepository extends BaseRepository<PhoneContact, Long> {
}