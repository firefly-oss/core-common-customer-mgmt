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
import com.firefly.core.customer.interfaces.dtos.ConsentDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing consents.
 */
public interface ConsentService {
    /**
     * Filters the consents based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party owning the consents
     * @param filterRequest the request object containing filtering criteria for ConsentDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of consents
     */
    Mono<PaginationResponse<ConsentDTO>> filterConsents(UUID partyId, FilterRequest<ConsentDTO> filterRequest);
    
    /**
     * Creates a new consent based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the consent
     * @param consentDTO the DTO object containing details of the consent to be created
     * @return a Mono that emits the created ConsentDTO object
     */
    Mono<ConsentDTO> createConsent(UUID partyId, ConsentDTO consentDTO);
    
    /**
     * Updates an existing consent with updated information.
     *
     * @param partyId the unique identifier of the party that owns the consent
     * @param consentId the unique identifier of the consent to be updated
     * @param consentDTO the data transfer object containing the updated details of the consent
     * @return a reactive Mono containing the updated ConsentDTO
     */
    Mono<ConsentDTO> updateConsent(UUID partyId, UUID consentId, ConsentDTO consentDTO);
    
    /**
     * Deletes a consent identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the consent
     * @param consentId the unique identifier of the consent to be deleted
     * @return a Mono that completes when the consent is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteConsent(UUID partyId, UUID consentId);
    
    /**
     * Retrieves a consent by its unique identifier.
     *
     * @param partyId the unique identifier of the party that owns the consent
     * @param consentId the unique identifier of the consent to retrieve
     * @return a Mono emitting the {@link ConsentDTO} representing the consent if found,
     *         or an empty Mono if the consent does not exist
     */
    Mono<ConsentDTO> getConsentById(UUID partyId, UUID consentId);
}