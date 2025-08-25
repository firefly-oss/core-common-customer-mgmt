package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PartyGroupMembershipMapper;
import com.catalis.core.customer.core.services.PartyGroupMembershipService;
import com.catalis.core.customer.interfaces.dtos.PartyGroupMembershipDTO;
import com.catalis.core.customer.models.entities.PartyGroupMembership;
import com.catalis.core.customer.models.repositories.PartyGroupMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PartyGroupMembershipServiceImpl implements PartyGroupMembershipService {

    @Autowired
    private PartyGroupMembershipRepository repository;

    @Autowired
    private PartyGroupMembershipMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyGroupMembershipDTO>> filterPartyGroupMemberships(Long partyId, FilterRequest<PartyGroupMembershipDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyGroupMembership.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyGroupMembershipDTO> createPartyGroupMembership(Long partyId, PartyGroupMembershipDTO partyGroupMembershipDTO) {
        return Mono.just(partyGroupMembershipDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyGroupMembershipDTO> updatePartyGroupMembership(Long partyId, Long partyGroupMembershipId, PartyGroupMembershipDTO partyGroupMembershipDTO) {
        return repository.findById(partyGroupMembershipId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party group membership not found with ID: " + partyGroupMembershipId)))
                .flatMap(existingPartyGroupMembership -> {
                    PartyGroupMembership updatedPartyGroupMembership = mapper.toEntity(partyGroupMembershipDTO);
                    updatedPartyGroupMembership.setPartyGroupMembershipId(partyGroupMembershipId);
                    return repository.save(updatedPartyGroupMembership);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePartyGroupMembership(Long partyId, Long partyGroupMembershipId) {
        return repository.findById(partyGroupMembershipId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party group membership not found with ID: " + partyGroupMembershipId)))
                .flatMap(partyGroupMembership -> repository.deleteById(partyGroupMembershipId));
    }

    @Override
    public Mono<PartyGroupMembershipDTO> getPartyGroupMembershipById(Long partyId, Long partyGroupMembershipId) {
        return repository.findById(partyGroupMembershipId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party group membership not found with ID: " + partyGroupMembershipId)))
                .map(mapper::toDTO);
    }
}