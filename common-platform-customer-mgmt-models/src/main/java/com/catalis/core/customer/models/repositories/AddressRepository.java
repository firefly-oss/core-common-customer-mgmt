package com.catalis.core.customer.models.repositories;

import com.catalis.core.customer.models.entities.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseRepository<Address, Long> {
}