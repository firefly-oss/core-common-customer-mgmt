package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PartyStatusMapper;
import com.catalis.core.customer.core.services.PartyStatusService;
import com.catalis.core.customer.interfaces.dtos.PartyStatusDTO;
import com.catalis.core.customer.models.entities.PartyStatus;
import com.catalis.core.customer.models.repositories.PartyStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PartyStatusServiceImpl implements PartyStatusService {

    @Autowired
    private PartyStatusRepository repository;

    @Autowired
    private PartyStatusMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyStatusDTO>> filterPartyStatuses(FilterRequest<PartyStatusDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyStatus.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyStatusDTO> createPartyStatus(PartyStatusDTO partyStatusDTO) {
        return Mono.just(partyStatusDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyStatusDTO> updatePartyStatus(Long partyStatusId, PartyStatusDTO partyStatusDTO) {
        return repository.findById(partyStatusId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party status not found with ID: " + partyStatusId)))
                .flatMap(existingPartyStatus -> {
                    PartyStatus updatedPartyStatus = mapper.toEntity(partyStatusDTO);
                    updatedPartyStatus.setPartyStatusId(partyStatusId);
                    return repository.save(updatedPartyStatus);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePartyStatus(Long partyStatusId) {
        return repository.findById(partyStatusId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party status not found with ID: " + partyStatusId)))
                .flatMap(partyStatus -> repository.deleteById(partyStatusId));
    }

    @Override
    public Mono<PartyStatusDTO> getPartyStatusById(Long partyStatusId) {
        return repository.findById(partyStatusId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party status not found with ID: " + partyStatusId)))
                .map(mapper::toDTO);
    }
}