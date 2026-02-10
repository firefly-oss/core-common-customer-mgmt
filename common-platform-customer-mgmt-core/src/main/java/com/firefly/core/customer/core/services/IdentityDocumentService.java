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
import com.firefly.core.customer.interfaces.dtos.IdentityDocumentDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing identity documents.
 */
public interface IdentityDocumentService {
    /**
     * Filters the identity documents based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party owning the identity documents
     * @param filterRequest the request object containing filtering criteria for IdentityDocumentDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of identity documents
     */
    Mono<PaginationResponse<IdentityDocumentDTO>> filterIdentityDocuments(UUID partyId, FilterRequest<IdentityDocumentDTO> filterRequest);
    
    /**
     * Creates a new identity document based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the identity document
     * @param identityDocumentDTO the DTO object containing details of the identity document to be created
     * @return a Mono that emits the created IdentityDocumentDTO object
     */
    Mono<IdentityDocumentDTO> createIdentityDocument(UUID partyId, IdentityDocumentDTO identityDocumentDTO);
    
    /**
     * Updates an existing identity document with updated information.
     *
     * @param partyId the unique identifier of the party that owns the identity document
     * @param identityDocumentId the unique identifier of the identity document to be updated
     * @param identityDocumentDTO the data transfer object containing the updated details of the identity document
     * @return a reactive Mono containing the updated IdentityDocumentDTO
     */
    Mono<IdentityDocumentDTO> updateIdentityDocument(UUID partyId, UUID identityDocumentId, IdentityDocumentDTO identityDocumentDTO);
    
    /**
     * Deletes an identity document identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the identity document
     * @param identityDocumentId the unique identifier of the identity document to be deleted
     * @return a Mono that completes when the identity document is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteIdentityDocument(UUID partyId, UUID identityDocumentId);
    
    /**
     * Retrieves an identity document by its unique identifier.
     *
     * @param partyId the unique identifier of the party that owns the identity document
     * @param identityDocumentId the unique identifier of the identity document to retrieve
     * @return a Mono emitting the {@link IdentityDocumentDTO} representing the identity document if found,
     *         or an empty Mono if the identity document does not exist
     */
    Mono<IdentityDocumentDTO> getIdentityDocumentById(UUID partyId, UUID identityDocumentId);
}