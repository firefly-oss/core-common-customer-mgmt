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
import com.firefly.core.customer.interfaces.dtos.PartyGroupMembershipDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing party group memberships.
 */
public interface PartyGroupMembershipService {
    /**
     * Filters the party group memberships based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party
     * @param filterRequest the request object containing filtering criteria for PartyGroupMembershipDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party group memberships
     */
    Mono<PaginationResponse<PartyGroupMembershipDTO>> filterPartyGroupMemberships(UUID partyId, FilterRequest<PartyGroupMembershipDTO> filterRequest);
    
    /**
     * Creates a new party group membership based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the party group membership
     * @param partyGroupMembershipDTO the DTO object containing details of the party group membership to be created
     * @return a Mono that emits the created PartyGroupMembershipDTO object
     */
    Mono<PartyGroupMembershipDTO> createPartyGroupMembership(UUID partyId, PartyGroupMembershipDTO partyGroupMembershipDTO);
    
    /**
     * Updates an existing party group membership with updated information.
     *
     * @param partyId the unique identifier of the party that owns the party group membership
     * @param partyGroupMembershipId the unique identifier of the party group membership to be updated
     * @param partyGroupMembershipDTO the data transfer object containing the updated details of the party group membership
     * @return a reactive Mono containing the updated PartyGroupMembershipDTO
     */
    Mono<PartyGroupMembershipDTO> updatePartyGroupMembership(UUID partyId, UUID partyGroupMembershipId, PartyGroupMembershipDTO partyGroupMembershipDTO);
    
    /**
     * Deletes a party group membership identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the party group membership
     * @param partyGroupMembershipId the unique identifier of the party group membership to be deleted
     * @return a Mono that completes when the party group membership is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyGroupMembership(UUID partyId, UUID partyGroupMembershipId);
    
    /**
     * Retrieves a party group membership by its unique identifier for a specific party.
     *
     * @param partyId the unique identifier of the party that owns the party group membership
     * @param partyGroupMembershipId the unique identifier of the party group membership to retrieve
     * @return a Mono emitting the {@link PartyGroupMembershipDTO} representing the party group membership if found,
     *         or an empty Mono if the party group membership does not exist
     */
    Mono<PartyGroupMembershipDTO> getPartyGroupMembershipById(UUID partyId, UUID partyGroupMembershipId);
}