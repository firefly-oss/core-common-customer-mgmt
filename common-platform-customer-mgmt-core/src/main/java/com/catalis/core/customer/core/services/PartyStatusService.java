package com.catalis.core.customer.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.interfaces.dtos.PartyStatusDTO;
import reactor.core.publisher.Mono;

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
    Mono<PaginationResponse<PartyStatusDTO>> filterPartyStatuses(Long partyId, FilterRequest<PartyStatusDTO> filterRequest);
    
    /**
     * Creates a new party status based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the party status
     * @param partyStatusDTO the DTO object containing details of the party status to be created
     * @return a Mono that emits the created PartyStatusDTO object
     */
    Mono<PartyStatusDTO> createPartyStatus(Long partyId, PartyStatusDTO partyStatusDTO);
    
    /**
     * Updates an existing party status with updated information.
     *
     * @param partyId the unique identifier of the party that owns the party status
     * @param partyStatusDTO the data transfer object containing the updated details of the party status
     * @return a reactive Mono containing the updated PartyStatusDTO
     */
    Mono<PartyStatusDTO> updatePartyStatus(Long partyId, PartyStatusDTO partyStatusDTO);
    
    /**
     * Deletes a party status identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the party status
     * @param partyStatusId the unique identifier of the party status to be deleted
     * @return a Mono that completes when the party status is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyStatus(Long partyId, Long partyStatusId);
    
    /**
     * Retrieves a party status by its unique identifier for a specific party.
     *
     * @param partyId the unique identifier of the party that owns the party status
     * @param partyStatusId the unique identifier of the party status to retrieve
     * @return a Mono emitting the {@link PartyStatusDTO} representing the party status if found,
     *         or an empty Mono if the party status does not exist
     */
    Mono<PartyStatusDTO> getPartyStatusById(Long partyId, Long partyStatusId);
}