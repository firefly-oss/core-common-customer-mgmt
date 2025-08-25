package com.catalis.core.customer.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.interfaces.dtos.PartyGroupMembershipDTO;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing party group memberships.
 */
public interface PartyGroupMembershipService {
    /**
     * Filters the party group memberships based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party
     * @param filterRequest the request object containing filtering criteria for PartyGroupMembershipDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of party group memberships
     */
    Mono<PaginationResponse<PartyGroupMembershipDTO>> filterPartyGroupMemberships(Long partyId, FilterRequest<PartyGroupMembershipDTO> filterRequest);
    
    /**
     * Creates a new party group membership based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the party group membership
     * @param partyGroupMembershipDTO the DTO object containing details of the party group membership to be created
     * @return a Mono that emits the created PartyGroupMembershipDTO object
     */
    Mono<PartyGroupMembershipDTO> createPartyGroupMembership(Long partyId, PartyGroupMembershipDTO partyGroupMembershipDTO);
    
    /**
     * Updates an existing party group membership with updated information.
     *
     * @param partyId the unique identifier of the party that owns the party group membership
     * @param partyGroupMembershipId the unique identifier of the party group membership to be updated
     * @param partyGroupMembershipDTO the data transfer object containing the updated details of the party group membership
     * @return a reactive Mono containing the updated PartyGroupMembershipDTO
     */
    Mono<PartyGroupMembershipDTO> updatePartyGroupMembership(Long partyId, Long partyGroupMembershipId, PartyGroupMembershipDTO partyGroupMembershipDTO);
    
    /**
     * Deletes a party group membership identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the party group membership
     * @param partyGroupMembershipId the unique identifier of the party group membership to be deleted
     * @return a Mono that completes when the party group membership is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePartyGroupMembership(Long partyId, Long partyGroupMembershipId);
    
    /**
     * Retrieves a party group membership by its unique identifier for a specific party.
     *
     * @param partyId the unique identifier of the party that owns the party group membership
     * @param partyGroupMembershipId the unique identifier of the party group membership to retrieve
     * @return a Mono emitting the {@link PartyGroupMembershipDTO} representing the party group membership if found,
     *         or an empty Mono if the party group membership does not exist
     */
    Mono<PartyGroupMembershipDTO> getPartyGroupMembershipById(Long partyId, Long partyGroupMembershipId);
}