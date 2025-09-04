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
import com.firefly.core.customer.interfaces.dtos.PartyRelationshipDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing party relationships.
 */
public interface PartyRelationshipService {
    /**
     * Filters the party relationships based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party owning the relationships
     * @param filterRequest the request object containing filtering criteria for PartyRelationshipDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party relationships
     */
    Mono<PaginationResponse<PartyRelationshipDTO>> filterPartyRelationships(UUID partyId, FilterRequest<PartyRelationshipDTO> filterRequest);
    
    /**
     * Creates a new party relationship based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the relationship
     * @param partyRelationshipDTO the DTO object containing details of the party relationship to be created
     * @return a Mono that emits the created PartyRelationshipDTO object
     */
    Mono<PartyRelationshipDTO> createPartyRelationship(UUID partyId, PartyRelationshipDTO partyRelationshipDTO);
    
    /**
     * Updates an existing party relationship with updated information.
     *
     * @param partyId the unique identifier of the party that owns the relationship
     * @param partyRelationshipId the unique identifier of the party relationship to be updated
     * @param partyRelationshipDTO the data transfer object containing the updated details of the party relationship
     * @return a reactive Mono containing the updated PartyRelationshipDTO
     */
    Mono<PartyRelationshipDTO> updatePartyRelationship(UUID partyId, UUID partyRelationshipId, PartyRelationshipDTO partyRelationshipDTO);
    
    /**
     * Deletes a party relationship identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the relationship
     * @param partyRelationshipId the unique identifier of the party relationship to be deleted
     * @return a Mono that completes when the party relationship is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyRelationship(UUID partyId, UUID partyRelationshipId);
    
    /**
     * Retrieves a party relationship by its unique identifier.
     *
     * @param partyId the unique identifier of the party that owns the relationship
     * @param partyRelationshipId the unique identifier of the party relationship to retrieve
     * @return a Mono emitting the {@link PartyRelationshipDTO} representing the party relationship if found,
     *         or an empty Mono if the party relationship does not exist
     */
    Mono<PartyRelationshipDTO> getPartyRelationshipById(UUID partyId, UUID partyRelationshipId);
}