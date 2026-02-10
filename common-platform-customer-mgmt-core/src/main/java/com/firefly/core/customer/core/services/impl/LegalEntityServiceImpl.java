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
import com.firefly.core.customer.core.mappers.LegalEntityMapper;
import com.firefly.core.customer.core.services.LegalEntityService;
import com.firefly.core.customer.interfaces.dtos.LegalEntityDTO;
import com.firefly.core.customer.models.entities.LegalEntity;
import com.firefly.core.customer.models.repositories.LegalEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class LegalEntityServiceImpl implements LegalEntityService {

    @Autowired
    private LegalEntityRepository repository;

    @Autowired
    private LegalEntityMapper mapper;

    @Override
    public Mono<PaginationResponse<LegalEntityDTO>> filterLegalEntities(FilterRequest<LegalEntityDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        LegalEntity.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<LegalEntityDTO> createLegalEntity(UUID partyId, LegalEntityDTO legalEntityDTO) {
        return Mono.just(legalEntityDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LegalEntityDTO> updateLegalEntity(UUID partyId, UUID legalEntityId, LegalEntityDTO legalEntityDTO) {
        return repository.findById(legalEntityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Legal entity not found with ID: " + legalEntityId)))
                .flatMap(existingLegalEntity -> {
                    // Validate that the natural person belongs to the specified party
                    if (!partyId.equals(existingLegalEntity.getPartyId())) {
                        return Mono.error(new RuntimeException("Legal entity with ID " + legalEntityId + " does not belong to party " + partyId));
                    }
                    mapper.updateEntityFromDto(legalEntityDTO, existingLegalEntity);
                    return repository.save(existingLegalEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteLegalEntity(UUID partyId, UUID legalEntityId) {
        return repository.findById(legalEntityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Legal entity not found with ID: " + legalEntityId)))
                .flatMap(legalEntity -> repository.deleteById(legalEntityId));
    }

    @Override
    public Mono<LegalEntityDTO> getLegalEntityById(UUID partyId, UUID legalEntityId) {
        return repository.findById(legalEntityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Legal entity not found with ID: " + legalEntityId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LegalEntityDTO> getLegalEntityByPartyId(UUID partyId) {
        return repository.findByPartyId(partyId)
                .map(mapper::toDTO)
                .next();
    }
}