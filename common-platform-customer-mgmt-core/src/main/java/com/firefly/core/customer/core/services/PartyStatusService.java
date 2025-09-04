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
import com.firefly.core.customer.interfaces.dtos.PartyStatusDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing party statuses.
 */
public interface PartyStatusService {
    /**
     * Filters the party statuses based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party
     * @param filterRequest the request object containing filtering criteria for PartyStatusDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party statuses
     */
    Mono<PaginationResponse<PartyStatusDTO>> filterPartyStatuses(UUID partyId, FilterRequest<PartyStatusDTO> filterRequest);
    
    /**
     * Creates a new party status based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the party status
     * @param partyStatusDTO the DTO object containing details of the party status to be created
     * @return a Mono that emits the created PartyStatusDTO object
     */
    Mono<PartyStatusDTO> createPartyStatus(UUID partyId, PartyStatusDTO partyStatusDTO);
    
    /**
     * Updates an existing party status with updated information.
     *
     * @param partyId the unique identifier of the party that owns the party status
     * @param partyStatusDTO the data transfer object containing the updated details of the party status
     * @return a reactive Mono containing the updated PartyStatusDTO
     */
    Mono<PartyStatusDTO> updatePartyStatus(UUID partyId, PartyStatusDTO partyStatusDTO);
    
    /**
     * Deletes a party status identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the party status
     * @param partyStatusId the unique identifier of the party status to be deleted
     * @return a Mono that completes when the party status is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyStatus(UUID partyId, UUID partyStatusId);
    
    /**
     * Retrieves a party status by its unique identifier for a specific party.
     *
     * @param partyId the unique identifier of the party that owns the party status
     * @param partyStatusId the unique identifier of the party status to retrieve
     * @return a Mono emitting the {@link PartyStatusDTO} representing the party status if found,
     *         or an empty Mono if the party status does not exist
     */
    Mono<PartyStatusDTO> getPartyStatusById(UUID partyId, UUID partyStatusId);
}