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


package com.firefly.core.customer.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.interfaces.dtos.LegalEntityDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing legal entities.
 */
public interface LegalEntityService {
    /**
     * Filters the legal entities based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for LegalEntityDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of legal entities
     */
    Mono<PaginationResponse<LegalEntityDTO>> filterLegalEntities(FilterRequest<LegalEntityDTO> filterRequest);
    
    /**
     * Creates a new legal entity based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the legal entity
     * @param legalEntityDTO the DTO object containing details of the legal entity to be created
     * @return a Mono that emits the created LegalEntityDTO object
     */
    Mono<LegalEntityDTO> createLegalEntity(UUID partyId, LegalEntityDTO legalEntityDTO);
    
    /**
     * Updates an existing legal entity with updated information.
     *
     * @param partyId the unique identifier of the party that owns the legal entity
     * @param legalEntityId the unique identifier of the legal entity to be updated
     * @param legalEntityDTO the data transfer object containing the updated details of the legal entity
     * @return a reactive Mono containing the updated LegalEntityDTO
     */
    Mono<LegalEntityDTO> updateLegalEntity(UUID partyId, UUID legalEntityId, LegalEntityDTO legalEntityDTO);
    
    /**
     * Deletes a legal entity identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the legal entity
     * @param legalEntityId the unique identifier of the legal entity to be deleted
     * @return a Mono that completes when the legal entity is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteLegalEntity(UUID partyId, UUID legalEntityId);
    
    /**
     * Retrieves a legal entity by its unique identifier.
     *
     * @param partyId the unique identifier of the party that owns the legal entity
     * @param legalEntityId the unique identifier of the legal entity to retrieve
     * @return a Mono emitting the {@link LegalEntityDTO} representing the legal entity if found,
     *         or an empty Mono if the legal entity does not exist
     */
    Mono<LegalEntityDTO> getLegalEntityById(UUID partyId, UUID legalEntityId);
    
    /**
     * Retrieves the legal entity associated with a specific party.
     *
     * @param partyId the unique identifier of the party
     * @return a Mono emitting the LegalEntityDTO object belonging to the specified party
     */
    Mono<LegalEntityDTO> getLegalEntityByPartyId(UUID partyId);
}