package com.catalis.core.customer.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.interfaces.dtos.PartyDTO;
import reactor.core.publisher.Mono;

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
    Mono<PartyDTO> updateParty(Long partyId, PartyDTO partyDTO);
    
    /**
     * Deletes a party identified by its unique ID.
     *
     * @param partyId the unique identifier of the party to be deleted
     * @return a Mono that completes when the party is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteParty(Long partyId);
    
    /**
     * Retrieves a party by its unique identifier.
     *
     * @param partyId the unique identifier of the party to retrieve
     * @return a Mono emitting the {@link PartyDTO} representing the party if found,
     *         or an empty Mono if the party does not exist
     */
    Mono<PartyDTO> getPartyById(Long partyId);
}