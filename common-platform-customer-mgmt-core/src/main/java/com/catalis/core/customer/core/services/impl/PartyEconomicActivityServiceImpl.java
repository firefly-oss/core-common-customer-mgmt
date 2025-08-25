package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PartyEconomicActivityMapper;
import com.catalis.core.customer.core.services.PartyEconomicActivityService;
import com.catalis.core.customer.interfaces.dtos.PartyEconomicActivityDTO;
import com.catalis.core.customer.models.entities.PartyEconomicActivity;
import com.catalis.core.customer.models.repositories.PartyEconomicActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PartyEconomicActivityServiceImpl implements PartyEconomicActivityService {

    @Autowired
    private PartyEconomicActivityRepository repository;

    @Autowired
    private PartyEconomicActivityMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyEconomicActivityDTO>> filterPartyEconomicActivities(Long partyId, FilterRequest<PartyEconomicActivityDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyEconomicActivity.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyEconomicActivityDTO> createPartyEconomicActivity(Long partyId, PartyEconomicActivityDTO partyEconomicActivityDTO) {
        return Mono.just(partyEconomicActivityDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyEconomicActivityDTO> updatePartyEconomicActivity(Long partyId, Long partyEconomicActivityId, PartyEconomicActivityDTO partyEconomicActivityDTO) {
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
    public Mono<Void> deletePartyEconomicActivity(Long partyId, Long partyEconomicActivityId) {
        return repository.findById(partyEconomicActivityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party economic activity not found with ID: " + partyEconomicActivityId)))
                .flatMap(partyEconomicActivity -> repository.deleteById(partyEconomicActivityId));
    }

    @Override
    public Mono<PartyEconomicActivityDTO> getPartyEconomicActivityById(Long partyId, Long partyEconomicActivityId) {
        return repository.findById(partyEconomicActivityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party economic activity not found with ID: " + partyEconomicActivityId)))
                .map(mapper::toDTO);
    }
}