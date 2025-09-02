package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PartyEconomicActivityMapper;
import com.firefly.core.customer.core.services.PartyEconomicActivityService;
import com.firefly.core.customer.interfaces.dtos.PartyEconomicActivityDTO;
import com.firefly.core.customer.models.entities.PartyEconomicActivity;
import com.firefly.core.customer.models.repositories.PartyEconomicActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class PartyEconomicActivityServiceImpl implements PartyEconomicActivityService {

    @Autowired
    private PartyEconomicActivityRepository repository;

    @Autowired
    private PartyEconomicActivityMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyEconomicActivityDTO>> filterPartyEconomicActivities(UUID partyId, FilterRequest<PartyEconomicActivityDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyEconomicActivity.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyEconomicActivityDTO> createPartyEconomicActivity(UUID partyId, PartyEconomicActivityDTO partyEconomicActivityDTO) {
        return Mono.just(partyEconomicActivityDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyEconomicActivityDTO> updatePartyEconomicActivity(UUID partyId, UUID partyEconomicActivityId, PartyEconomicActivityDTO partyEconomicActivityDTO) {
        return repository.findById(partyEconomicActivityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party economic activity not found with ID: " + partyEconomicActivityId)))
                .flatMap(existingPartyEconomicActivity -> {
                    PartyEconomicActivity updatedPartyEconomicActivity = mapper.toEntity(partyEconomicActivityDTO);
                    updatedPartyEconomicActivity.setPartyEconomicActivityId(partyEconomicActivityId);
                    return repository.save(updatedPartyEconomicActivity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePartyEconomicActivity(UUID partyId, UUID partyEconomicActivityId) {
        return repository.findById(partyEconomicActivityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party economic activity not found with ID: " + partyEconomicActivityId)))
                .flatMap(partyEconomicActivity -> repository.deleteById(partyEconomicActivityId));
    }

    @Override
    public Mono<PartyEconomicActivityDTO> getPartyEconomicActivityById(UUID partyId, UUID partyEconomicActivityId) {
        return repository.findById(partyEconomicActivityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party economic activity not found with ID: " + partyEconomicActivityId)))
                .map(mapper::toDTO);
    }
}