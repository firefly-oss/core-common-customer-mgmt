package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.Address;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AddressRepository extends BaseRepository<Address, UUID> {
}