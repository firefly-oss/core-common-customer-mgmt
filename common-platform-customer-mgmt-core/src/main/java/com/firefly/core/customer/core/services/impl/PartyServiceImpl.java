package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PartyMapper;
import com.firefly.core.customer.core.services.PartyService;
import com.firefly.core.customer.interfaces.dtos.PartyDTO;
import com.firefly.core.customer.models.entities.Party;
import com.firefly.core.customer.models.repositories.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PartyServiceImpl implements PartyService {

    @Autowired
    private PartyRepository repository;

    @Autowired
    private PartyMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyDTO>> filterParties(FilterRequest<PartyDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Party.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyDTO> createParty(PartyDTO partyDTO) {
        return Mono.just(partyDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyDTO> updateParty(Long partyId, PartyDTO partyDTO) {
        return repository.findById(partyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party not found with ID: " + partyId)))
                .flatMap(existingParty -> {
                    Party updatedParty = mapper.toEntity(partyDTO);
                    updatedParty.setPartyId(partyId);
                    return repository.save(updatedParty);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteParty(Long partyId) {
        return repository.findById(partyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party not found with ID: " + partyId)))
                .flatMap(party -> repository.deleteById(partyId));
    }

    @Override
    public Mono<PartyDTO> getPartyById(Long partyId) {
        return repository.findById(partyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party not found with ID: " + partyId)))
                .map(mapper::toDTO);
    }
}