package com.firefly.core.customer.models.repositories;

import com.firefly.core.customer.models.entities.PartyEconomicActivity;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PartyEconomicActivityRepository extends BaseRepository<PartyEconomicActivity, UUID> {
}