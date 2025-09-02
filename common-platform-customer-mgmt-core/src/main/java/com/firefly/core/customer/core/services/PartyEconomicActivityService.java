package com.firefly.core.customer.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.interfaces.dtos.PartyEconomicActivityDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing party economic activities.
 */
public interface PartyEconomicActivityService {
    /**
     * Filters the party economic activities based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party
     * @param filterRequest the request object containing filtering criteria for PartyEconomicActivityDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party economic activities
     */
    Mono<PaginationResponse<PartyEconomicActivityDTO>> filterPartyEconomicActivities(UUID partyId, FilterRequest<PartyEconomicActivityDTO> filterRequest);
    
    /**
     * Creates a new party economic activity based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the party economic activity
     * @param partyEconomicActivityDTO the DTO object containing details of the party economic activity to be created
     * @return a Mono that emits the created PartyEconomicActivityDTO object
     */
    Mono<PartyEconomicActivityDTO> createPartyEconomicActivity(UUID partyId, PartyEconomicActivityDTO partyEconomicActivityDTO);
    
    /**
     * Updates an existing party economic activity with updated information.
     *
     * @param partyId the unique identifier of the party that owns the party economic activity
     * @param partyEconomicActivityId the unique identifier of the party economic activity to be updated
     * @param partyEconomicActivityDTO the data transfer object containing the updated details of the party economic activity
     * @return a reactive Mono containing the updated PartyEconomicActivityDTO
     */
    Mono<PartyEconomicActivityDTO> updatePartyEconomicActivity(UUID partyId, UUID partyEconomicActivityId, PartyEconomicActivityDTO partyEconomicActivityDTO);
    
    /**
     * Deletes a party economic activity identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the party economic activity
     * @param partyEconomicActivityId the unique identifier of the party economic activity to be deleted
     * @return a Mono that completes when the party economic activity is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyEconomicActivity(UUID partyId, UUID partyEconomicActivityId);
    
    /**
     * Retrieves a party economic activity by its unique identifier for a specific party.
     *
     * @param partyId the unique identifier of the party that owns the party economic activity
     * @param partyEconomicActivityId the unique identifier of the party economic activity to retrieve
     * @return a Mono emitting the {@link PartyEconomicActivityDTO} representing the party economic activity if found,
     *         or an empty Mono if the party economic activity does not exist
     */
    Mono<PartyEconomicActivityDTO> getPartyEconomicActivityById(UUID partyId, UUID partyEconomicActivityId);
}