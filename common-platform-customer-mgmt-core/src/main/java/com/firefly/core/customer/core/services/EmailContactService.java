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
import com.firefly.core.customer.interfaces.dtos.EmailContactDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing email contacts.
 */
public interface EmailContactService {
    /**
     * Filters the email contacts based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party owning the email contacts
     * @param filterRequest the request object containing filtering criteria for EmailContactDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of email contacts
     */
    Mono<PaginationResponse<EmailContactDTO>> filterEmailContacts(UUID partyId, FilterRequest<EmailContactDTO> filterRequest);
    
    /**
     * Creates a new email contact based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the email contact
     * @param emailContactDTO the DTO object containing details of the email contact to be created
     * @return a Mono that emits the created EmailContactDTO object
     */
    Mono<EmailContactDTO> createEmailContact(UUID partyId, EmailContactDTO emailContactDTO);
    
    /**
     * Updates an existing email contact with updated information.
     *
     * @param partyId the unique identifier of the party that owns the email contact
     * @param emailContactId the unique identifier of the email contact to be updated
     * @param emailContactDTO the data transfer object containing the updated details of the email contact
     * @return a reactive Mono containing the updated EmailContactDTO
     */
    Mono<EmailContactDTO> updateEmailContact(UUID partyId, UUID emailContactId, EmailContactDTO emailContactDTO);
    
    /**
     * Deletes an email contact identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the email contact
     * @param emailContactId the unique identifier of the email contact to be deleted
     * @return a Mono that completes when the email contact is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteEmailContact(UUID partyId, UUID emailContactId);
    
    /**
     * Retrieves an email contact by its unique identifier.
     *
     * @param partyId the unique identifier of the party that owns the email contact
     * @param emailContactId the unique identifier of the email contact to retrieve
     * @return a Mono emitting the {@link EmailContactDTO} representing the email contact if found,
     *         or an empty Mono if the email contact does not exist
     */
    Mono<EmailContactDTO> getEmailContactById(UUID partyId, UUID emailContactId);
}