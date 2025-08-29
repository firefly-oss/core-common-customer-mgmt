package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PartyStatusMapper;
import com.firefly.core.customer.core.services.PartyStatusService;
import com.firefly.core.customer.interfaces.dtos.PartyStatusDTO;
import com.firefly.core.customer.models.entities.PartyStatus;
import com.firefly.core.customer.models.repositories.PartyStatusRepository;
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
    public Mono<PaginationResponse<PartyStatusDTO>> filterPartyStatuses(Long partyId, FilterRequest<PartyStatusDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyStatus.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyStatusDTO> createPartyStatus(Long partyId, PartyStatusDTO partyStatusDTO) {
        return Mono.just(partyStatusDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyStatusDTO> updatePartyStatus(Long partyId, PartyStatusDTO partyStatusDTO) {
        return repository.findByPartyId(partyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party status not found for party ID: " + partyId)))
                .flatMap(existingPartyStatus -> {
                    // Validate that the natural person belongs to the specified party
                    if (!partyId.equals(existingPartyStatus.getPartyId())) {
                        return Mono.error(new RuntimeException("Status with ID " + existingPartyStatus.getPartyStatusId() + " does not belong to party " + partyId));
                    }
                    mapper.updateEntityFromDto(partyStatusDTO, existingPartyStatus);
                    return repository.save(existingPartyStatus);

                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePartyStatus(Long partyId, Long partyStatusId) {
        return repository.findById(partyStatusId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party status not found with ID: " + partyStatusId)))
                .flatMap(partyStatus -> repository.deleteById(partyStatusId));
    }

    @Override
    public Mono<PartyStatusDTO> getPartyStatusById(Long partyId, Long partyStatusId) {
        return repository.findById(partyStatusId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party status not found with ID: " + partyStatusId)))
                .map(mapper::toDTO);
    }
}