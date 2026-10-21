/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.customer.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.customer.interfaces.dtos.PartyAncestorDTO;
import io.fireflysoftware.core.customer.interfaces.dtos.PartyRelationshipDTO;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing party relationships.
 */
public interface PartyRelationshipService {
    /**
     * Filters the party relationships based on the given criteria for a specific party.
     *
     * @param filterRequest the request object containing filtering criteria for PartyRelationshipDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party relationships
     */
    Mono<PaginationResponse<PartyRelationshipDTO>> filterPartyRelationships(FilterRequest<PartyRelationshipDTO> filterRequest);
    
    /**
     * Creates a new party relationship based on the provided information for a specific party.
     *
     * @param partyRelationshipDTO the DTO object containing details of the party relationship to be created
     * @return a Mono that emits the created PartyRelationshipDTO object
     */
    Mono<PartyRelationshipDTO> createPartyRelationship(PartyRelationshipDTO partyRelationshipDTO);
    
    /**
     * Updates an existing party relationship with updated information.
     *
     * @param partyRelationshipId the unique identifier of the party relationship to be updated
     * @param partyRelationshipDTO the data transfer object containing the updated details of the party relationship
     * @return a reactive Mono containing the updated PartyRelationshipDTO
     */
    Mono<PartyRelationshipDTO> updatePartyRelationship(UUID partyRelationshipId, PartyRelationshipDTO partyRelationshipDTO);
    
    /**
     * Deletes a party relationship identified by its unique ID, validating party ownership.
     *
     * @param partyRelationshipId the unique identifier of the party relationship to be deleted
     * @return a Mono that completes when the party relationship is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyRelationship(UUID partyRelationshipId);
    
    /**
     * Retrieves a party relationship by its unique identifier.
     *
     * @param partyRelationshipId the unique identifier of the party relationship to retrieve
     * @return a Mono emitting the {@link PartyRelationshipDTO} representing the party relationship if found,
     *         or an empty Mono if the party relationship does not exist
     */
    Mono<PartyRelationshipDTO> getPartyRelationshipById(UUID partyRelationshipId);

    /**
     * Ownership edges whose target is the legal entity. Several owners are normal.
     */
    Mono<List<PartyRelationshipDTO>> directOwners(UUID legalEntityPartyId);

    /**
     * Direct children: parent edges, and ownership edges whose source is a legal entity.
     */
    Mono<List<PartyRelationshipDTO>> directSubsidiaries(UUID legalEntityPartyId);

    /**
     * Legal entities above this one, nearest first, capped at {@code depth} and at 25.
     */
    Mono<List<PartyAncestorDTO>> ancestors(UUID legalEntityPartyId, int depth);
}