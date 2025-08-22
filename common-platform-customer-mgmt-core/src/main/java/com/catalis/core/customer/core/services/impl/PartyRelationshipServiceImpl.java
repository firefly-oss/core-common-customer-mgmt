package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PartyRelationshipMapper;
import com.catalis.core.customer.core.services.PartyRelationshipService;
import com.catalis.core.customer.interfaces.dtos.PartyRelationshipDTO;
import com.catalis.core.customer.models.entities.PartyRelationship;
import com.catalis.core.customer.models.repositories.PartyRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PartyRelationshipServiceImpl implements PartyRelationshipService {

    @Autowired
    private PartyRelationshipRepository repository;

    @Autowired
    private PartyRelationshipMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyRelationshipDTO>> filterPartyRelationships(FilterRequest<PartyRelationshipDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyRelationship.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyRelationshipDTO> createPartyRelationship(PartyRelationshipDTO partyRelationshipDTO) {
        return Mono.just(partyRelationshipDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyRelationshipDTO> updatePartyRelationship(Long partyRelationshipId, PartyRelationshipDTO partyRelationshipDTO) {
        return repository.findById(partyRelationshipId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party relationship not found with ID: " + partyRelationshipId)))
                .flatMap(existingPartyRelationship -> {
                    PartyRelationship updatedPartyRelationship = mapper.toEntity(partyRelationshipDTO);
                    updatedPartyRelationship.setPartyRelationshipId(partyRelationshipId);
                    return repository.save(updatedPartyRelationship);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePartyRelationship(Long partyRelationshipId) {
        return repository.findById(partyRelationshipId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party relationship not found with ID: " + partyRelationshipId)))
                .flatMap(partyRelationship -> repository.deleteById(partyRelationshipId));
    }

    @Override
    public Mono<PartyRelationshipDTO> getPartyRelationshipById(Long partyRelationshipId) {
        return repository.findById(partyRelationshipId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party relationship not found with ID: " + partyRelationshipId)))
                .map(mapper::toDTO);
    }
}