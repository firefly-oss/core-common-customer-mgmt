package com.catalis.core.customer.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.interfaces.dtos.PartyProviderDTO;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing party providers.
 */
public interface PartyProviderService {
    /**
     * Filters the party providers based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for PartyProviderDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party providers
     */
    Mono<PaginationResponse<PartyProviderDTO>> filterPartyProviders(FilterRequest<PartyProviderDTO> filterRequest);
    
    /**
     * Creates a new party provider based on the provided information.
     *
     * @param partyProviderDTO the DTO object containing details of the party provider to be created
     * @return a Mono that emits the created PartyProviderDTO object
     */
    Mono<PartyProviderDTO> createPartyProvider(PartyProviderDTO partyProviderDTO);
    
    /**
     * Updates an existing party provider with updated information.
     *
     * @param partyProviderId the unique identifier of the party provider to be updated
     * @param partyProviderDTO the data transfer object containing the updated details of the party provider
     * @return a reactive Mono containing the updated PartyProviderDTO
     */
    Mono<PartyProviderDTO> updatePartyProvider(Long partyProviderId, PartyProviderDTO partyProviderDTO);
    
    /**
     * Deletes a party provider identified by its unique ID.
     *
     * @param partyProviderId the unique identifier of the party provider to be deleted
     * @return a Mono that completes when the party provider is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyProvider(Long partyProviderId);
    
    /**
     * Retrieves a party provider by its unique identifier.
     *
     * @param partyProviderId the unique identifier of the party provider to retrieve
     * @return a Mono emitting the {@link PartyProviderDTO} representing the party provider if found,
     *         or an empty Mono if the party provider does not exist
     */
    Mono<PartyProviderDTO> getPartyProviderById(Long partyProviderId);
}