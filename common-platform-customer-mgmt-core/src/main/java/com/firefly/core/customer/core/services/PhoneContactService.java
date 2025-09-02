package com.firefly.core.customer.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.interfaces.dtos.PhoneContactDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing phone contacts.
 */
public interface PhoneContactService {
    /**
     * Filters the phone contacts based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party owning the phone contacts
     * @param filterRequest the request object containing filtering criteria for PhoneContactDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of phone contacts
     */
    Mono<PaginationResponse<PhoneContactDTO>> filterPhoneContacts(UUID partyId, FilterRequest<PhoneContactDTO> filterRequest);
    
    /**
     * Creates a new phone contact based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the phone contact
     * @param phoneContactDTO the DTO object containing details of the phone contact to be created
     * @return a Mono that emits the created PhoneContactDTO object
     */
    Mono<PhoneContactDTO> createPhoneContact(UUID partyId, PhoneContactDTO phoneContactDTO);
    
    /**
     * Updates an existing phone contact with updated information.
     *
     * @param partyId the unique identifier of the party that owns the phone contact
     * @param phoneContactId the unique identifier of the phone contact to be updated
     * @param phoneContactDTO the data transfer object containing the updated details of the phone contact
     * @return a reactive Mono containing the updated PhoneContactDTO
     */
    Mono<PhoneContactDTO> updatePhoneContact(UUID partyId, UUID phoneContactId, PhoneContactDTO phoneContactDTO);
    
    /**
     * Deletes a phone contact identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the phone contact
     * @param phoneContactId the unique identifier of the phone contact to be deleted
     * @return a Mono that completes when the phone contact is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePhoneContact(UUID partyId, UUID phoneContactId);
    
    /**
     * Retrieves a phone contact by its unique identifier.
     *
     * @param partyId the unique identifier of the party that owns the phone contact
     * @param phoneContactId the unique identifier of the phone contact to retrieve
     * @return a Mono emitting the {@link PhoneContactDTO} representing the phone contact if found,
     *         or an empty Mono if the phone contact does not exist
     */
    Mono<PhoneContactDTO> getPhoneContactById(UUID partyId, UUID phoneContactId);
}