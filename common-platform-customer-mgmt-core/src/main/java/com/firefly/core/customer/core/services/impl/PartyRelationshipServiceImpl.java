package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PartyRelationshipMapper;
import com.firefly.core.customer.core.services.PartyRelationshipService;
import com.firefly.core.customer.interfaces.dtos.PartyRelationshipDTO;
import com.firefly.core.customer.models.entities.PartyRelationship;
import com.firefly.core.customer.models.repositories.PartyRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class PartyRelationshipServiceImpl implements PartyRelationshipService {

    @Autowired
    private PartyRelationshipRepository repository;

    @Autowired
    private PartyRelationshipMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyRelationshipDTO>> filterPartyRelationships(UUID partyId, FilterRequest<PartyRelationshipDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyRelationship.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyRelationshipDTO> createPartyRelationship(UUID partyId, PartyRelationshipDTO partyRelationshipDTO) {
        return Mono.just(partyRelationshipDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyRelationshipDTO> updatePartyRelationship(UUID partyId, UUID partyRelationshipId, PartyRelationshipDTO partyRelationshipDTO) {
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
    public Mono<Void> deletePartyRelationship(UUID partyId, UUID partyRelationshipId) {
        return repository.findById(partyRelationshipId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party relationship not found with ID: " + partyRelationshipId)))
                .flatMap(partyRelationship -> repository.deleteById(partyRelationshipId));
    }

    @Override
    public Mono<PartyRelationshipDTO> getPartyRelationshipById(UUID partyId, UUID partyRelationshipId) {
        return repository.findById(partyRelationshipId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party relationship not found with ID: " + partyRelationshipId)))
                .map(mapper::toDTO);
    }
}