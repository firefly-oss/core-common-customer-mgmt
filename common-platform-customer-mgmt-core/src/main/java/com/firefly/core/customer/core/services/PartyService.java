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
import com.firefly.core.customer.interfaces.dtos.PartyDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing parties.
 */
public interface PartyService {
    /**
     * Filters the parties based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for PartyDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of parties
     */
    Mono<PaginationResponse<PartyDTO>> filterParties(FilterRequest<PartyDTO> filterRequest);
    
    /**
     * Creates a new party based on the provided information.
     *
     * @param partyDTO the DTO object containing details of the party to be created
     * @return a Mono that emits the created PartyDTO object
     */
    Mono<PartyDTO> createParty(PartyDTO partyDTO);
    
    /**
     * Updates an existing party with updated information.
     *
     * @param partyId the unique identifier of the party to be updated
     * @param partyDTO the data transfer object containing the updated details of the party
     * @return a reactive Mono containing the updated PartyDTO
     */
    Mono<PartyDTO> updateParty(UUID partyId, PartyDTO partyDTO);
    
    /**
     * Deletes a party identified by its unique ID.
     *
     * @param partyId the unique identifier of the party to be deleted
     * @return a Mono that completes when the party is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteParty(UUID partyId);
    
    /**
     * Retrieves a party by its unique identifier.
     *
     * @param partyId the unique identifier of the party to retrieve
     * @return a Mono emitting the {@link PartyDTO} representing the party if found,
     *         or an empty Mono if the party does not exist
     */
    Mono<PartyDTO> getPartyById(UUID partyId);
}