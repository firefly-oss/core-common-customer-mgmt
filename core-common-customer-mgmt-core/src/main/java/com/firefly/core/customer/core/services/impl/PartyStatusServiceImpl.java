/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.customer.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PartyStatusMapper;
import com.firefly.core.customer.core.services.PartyStatusService;
import com.firefly.core.customer.interfaces.dtos.PartyStatusDTO;
import com.firefly.core.customer.models.entities.PartyStatus;
import com.firefly.core.customer.models.repositories.PartyStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class PartyStatusServiceImpl implements PartyStatusService {

    @Autowired
    private PartyStatusRepository repository;

    @Autowired
    private PartyStatusMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyStatusDTO>> filterPartyStatuses(UUID partyId, FilterRequest<PartyStatusDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyStatus.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyStatusDTO> createPartyStatus(UUID partyId, PartyStatusDTO partyStatusDTO) {
        return Mono.just(partyStatusDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyStatusDTO> updatePartyStatus(UUID partyId, PartyStatusDTO partyStatusDTO) {
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
    public Mono<Void> deletePartyStatus(UUID partyId, UUID partyStatusId) {
        return repository.findById(partyStatusId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party status not found with ID: " + partyStatusId)))
                .flatMap(partyStatus -> repository.deleteById(partyStatusId));
    }

    @Override
    public Mono<PartyStatusDTO> getPartyStatusById(UUID partyId, UUID partyStatusId) {
        return repository.findById(partyStatusId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party status not found with ID: " + partyStatusId)))
                .map(mapper::toDTO);
    }
}