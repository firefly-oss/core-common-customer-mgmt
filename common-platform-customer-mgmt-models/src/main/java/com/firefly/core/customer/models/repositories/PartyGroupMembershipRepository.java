package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.PartyGroupMembership;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyGroupMembershipRepository extends BaseRepository<PartyGroupMembership, Long> {
}