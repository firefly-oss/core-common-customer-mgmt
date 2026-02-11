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

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.customer.interfaces.dtos.PartyProviderDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing party providers.
 */
public interface PartyProviderService {
    /**
     * Filters the party providers based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party
     * @param filterRequest the request object containing filtering criteria for PartyProviderDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party providers
     */
    Mono<PaginationResponse<PartyProviderDTO>> filterPartyProviders(UUID partyId, FilterRequest<PartyProviderDTO> filterRequest);
    
    /**
     * Creates a new party provider based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the party provider
     * @param partyProviderDTO the DTO object containing details of the party provider to be created
     * @return a Mono that emits the created PartyProviderDTO object
     */
    Mono<PartyProviderDTO> createPartyProvider(UUID partyId, PartyProviderDTO partyProviderDTO);
    
    /**
     * Updates an existing party provider with updated information.
     *
     * @param partyId the unique identifier of the party that owns the party provider
     * @param partyProviderId the unique identifier of the party provider to be updated
     * @param partyProviderDTO the data transfer object containing the updated details of the party provider
     * @return a reactive Mono containing the updated PartyProviderDTO
     */
    Mono<PartyProviderDTO> updatePartyProvider(UUID partyId, UUID partyProviderId, PartyProviderDTO partyProviderDTO);
    
    /**
     * Deletes a party provider identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the party provider
     * @param partyProviderId the unique identifier of the party provider to be deleted
     * @return a Mono that completes when the party provider is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyProvider(UUID partyId, UUID partyProviderId);
    
    /**
     * Retrieves a party provider by its unique identifier for a specific party.
     *
     * @param partyId the unique identifier of the party that owns the party provider
     * @param partyProviderId the unique identifier of the party provider to retrieve
     * @return a Mono emitting the {@link PartyProviderDTO} representing the party provider if found,
     *         or an empty Mono if the party provider does not exist
     */
    Mono<PartyProviderDTO> getPartyProviderById(UUID partyId, UUID partyProviderId);
}