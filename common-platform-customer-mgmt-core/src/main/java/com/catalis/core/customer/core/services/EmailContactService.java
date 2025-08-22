package com.catalis.core.customer.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.interfaces.dtos.EmailContactDTO;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing email contacts.
 */
public interface EmailContactService {
    /**
     * Filters the email contacts based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for EmailContactDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of email contacts
     */
    Mono<PaginationResponse<EmailContactDTO>> filterEmailContacts(FilterRequest<EmailContactDTO> filterRequest);
    
    /**
     * Creates a new email contact based on the provided information.
     *
     * @param emailContactDTO the DTO object containing details of the email contact to be created
     * @return a Mono that emits the created EmailContactDTO object
     */
    Mono<EmailContactDTO> createEmailContact(EmailContactDTO emailContactDTO);
    
    /**
     * Updates an existing email contact with updated information.
     *
     * @param emailContactId the unique identifier of the email contact to be updated
     * @param emailContactDTO the data transfer object containing the updated details of the email contact
     * @return a reactive Mono containing the updated EmailContactDTO
     */
    Mono<EmailContactDTO> updateEmailContact(Long emailContactId, EmailContactDTO emailContactDTO);
    
    /**
     * Deletes an email contact identified by its unique ID.
     *
     * @param emailContactId the unique identifier of the email contact to be deleted
     * @return a Mono that completes when the email contact is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteEmailContact(Long emailContactId);
    
    /**
     * Retrieves an email contact by its unique identifier.
     *
     * @param emailContactId the unique identifier of the email contact to retrieve
     * @return a Mono emitting the {@link EmailContactDTO} representing the email contact if found,
     *         or an empty Mono if the email contact does not exist
     */
    Mono<EmailContactDTO> getEmailContactById(Long emailContactId);
}