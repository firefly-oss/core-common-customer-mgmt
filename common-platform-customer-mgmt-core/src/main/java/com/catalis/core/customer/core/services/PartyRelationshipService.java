package com.catalis.core.customer.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.interfaces.dtos.PartyRelationshipDTO;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing party relationships.
 */
public interface PartyRelationshipService {
    /**
     * Filters the party relationships based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party owning the relationships
     * @param filterRequest the request object containing filtering criteria for PartyRelationshipDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party relationships
     */
    Mono<PaginationResponse<PartyRelationshipDTO>> filterPartyRelationships(Long partyId, FilterRequest<PartyRelationshipDTO> filterRequest);
    
    /**
     * Creates a new party relationship based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the relationship
     * @param partyRelationshipDTO the DTO object containing details of the party relationship to be created
     * @return a Mono that emits the created PartyRelationshipDTO object
     */
    Mono<PartyRelationshipDTO> createPartyRelationship(Long partyId, PartyRelationshipDTO partyRelationshipDTO);
    
    /**
     * Updates an existing party relationship with updated information.
     *
     * @param partyId the unique identifier of the party that owns the relationship
     * @param partyRelationshipId the unique identifier of the party relationship to be updated
     * @param partyRelationshipDTO the data transfer object containing the updated details of the party relationship
     * @return a reactive Mono containing the updated PartyRelationshipDTO
     */
    Mono<PartyRelationshipDTO> updatePartyRelationship(Long partyId, Long partyRelationshipId, PartyRelationshipDTO partyRelationshipDTO);
    
    /**
     * Deletes a party relationship identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the relationship
     * @param partyRelationshipId the unique identifier of the party relationship to be deleted
     * @return a Mono that completes when the party relationship is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyRelationship(Long partyId, Long partyRelationshipId);
    
    /**
     * Retrieves a party relationship by its unique identifier.
     *
     * @param partyId the unique identifier of the party that owns the relationship
     * @param partyRelationshipId the unique identifier of the party relationship to retrieve
     * @return a Mono emitting the {@link PartyRelationshipDTO} representing the party relationship if found,
     *         or an empty Mono if the party relationship does not exist
     */
    Mono<PartyRelationshipDTO> getPartyRelationshipById(Long partyId, Long partyRelationshipId);
}