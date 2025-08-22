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
     * Filters the party statuses based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for PartyStatusDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party statuses
     */
    Mono<PaginationResponse<PartyStatusDTO>> filterPartyStatuses(FilterRequest<PartyStatusDTO> filterRequest);
    
    /**
     * Creates a new party status based on the provided information.
     *
     * @param partyStatusDTO the DTO object containing details of the party status to be created
     * @return a Mono that emits the created PartyStatusDTO object
     */
    Mono<PartyStatusDTO> createPartyStatus(PartyStatusDTO partyStatusDTO);
    
    /**
     * Updates an existing party status with updated information.
     *
     * @param partyStatusId the unique identifier of the party status to be updated
     * @param partyStatusDTO the data transfer object containing the updated details of the party status
     * @return a reactive Mono containing the updated PartyStatusDTO
     */
    Mono<PartyStatusDTO> updatePartyStatus(Long partyStatusId, PartyStatusDTO partyStatusDTO);
    
    /**
     * Deletes a party status identified by its unique ID.
     *
     * @param partyStatusId the unique identifier of the party status to be deleted
     * @return a Mono that completes when the party status is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyStatus(Long partyStatusId);
    
    /**
     * Retrieves a party status by its unique identifier.
     *
     * @param partyStatusId the unique identifier of the party status to retrieve
     * @return a Mono emitting the {@link PartyStatusDTO} representing the party status if found,
     *         or an empty Mono if the party status does not exist
     */
    Mono<PartyStatusDTO> getPartyStatusById(Long partyStatusId);
}