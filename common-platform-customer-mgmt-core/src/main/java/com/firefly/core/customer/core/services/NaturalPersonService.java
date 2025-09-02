package com.firefly.core.customer.core.services;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.interfaces.dtos.NaturalPersonDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing natural persons.
 */
public interface NaturalPersonService {
    /**
     * Filters the natural persons based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party owning the natural persons
     * @param filterRequest the request object containing filtering criteria for NaturalPersonDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of natural persons
     */
    Mono<PaginationResponse<NaturalPersonDTO>> filterNaturalPersons(UUID partyId, FilterRequest<NaturalPersonDTO> filterRequest);
    
    /**
     * Creates a new natural person based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the natural person
     * @param naturalPersonDTO the DTO object containing details of the natural person to be created
     * @return a Mono that emits the created NaturalPersonDTO object
     */
    Mono<NaturalPersonDTO> createNaturalPerson(UUID partyId, NaturalPersonDTO naturalPersonDTO);
    
    /**
     * Updates an existing natural person with updated information, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the natural person
     * @param naturalPersonId the unique identifier of the natural person to be updated
     * @param naturalPersonDTO the data transfer object containing the updated details of the natural person
     * @return a reactive Mono containing the updated NaturalPersonDTO
     */
    Mono<NaturalPersonDTO> updateNaturalPerson(UUID partyId, UUID naturalPersonId, NaturalPersonDTO naturalPersonDTO);
    
    /**
     * Deletes a natural person identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the natural person
     * @param naturalPersonId the unique identifier of the natural person to be deleted
     * @return a Mono that completes when the natural person is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteNaturalPerson(UUID partyId, UUID naturalPersonId);
    
    /**
     * Retrieves a natural person by its unique identifier, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the natural person
     * @param naturalPersonId the unique identifier of the natural person to retrieve
     * @return a Mono emitting the {@link NaturalPersonDTO} representing the natural person if found,
     *         or an empty Mono if the natural person does not exist or doesn't belong to the party
     */
    Mono<NaturalPersonDTO> getNaturalPersonById(UUID partyId, UUID naturalPersonId);
    
    /**
     * Retrieves the natural person associated with a specific party.
     *
     * @param partyId the unique identifier of the party
     * @return a Mono emitting the NaturalPersonDTO object belonging to the specified party
     */
    Mono<NaturalPersonDTO> getNaturalPersonByPartyId(UUID partyId);
}