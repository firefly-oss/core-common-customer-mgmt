package com.catalis.core.customer.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.interfaces.dtos.PoliticallyExposedPersonDTO;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing politically exposed persons.
 */
public interface PoliticallyExposedPersonService {
    /**
     * Filters the politically exposed persons based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party
     * @param filterRequest the request object containing filtering criteria for PoliticallyExposedPersonDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of politically exposed persons
     */
    Mono<PaginationResponse<PoliticallyExposedPersonDTO>> filterPoliticallyExposedPersons(Long partyId, FilterRequest<PoliticallyExposedPersonDTO> filterRequest);
    
    /**
     * Creates a new politically exposed person based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the politically exposed person
     * @param politicallyExposedPersonDTO the DTO object containing details of the politically exposed person to be created
     * @return a Mono that emits the created PoliticallyExposedPersonDTO object
     */
    Mono<PoliticallyExposedPersonDTO> createPoliticallyExposedPerson(Long partyId, PoliticallyExposedPersonDTO politicallyExposedPersonDTO);
    
    /**
     * Updates an existing politically exposed person with updated information.
     *
     * @param partyId the unique identifier of the party that owns the politically exposed person
     * @param politicallyExposedPersonId the unique identifier of the politically exposed person to be updated
     * @param politicallyExposedPersonDTO the data transfer object containing the updated details of the politically exposed person
     * @return a reactive Mono containing the updated PoliticallyExposedPersonDTO
     */
    Mono<PoliticallyExposedPersonDTO> updatePoliticallyExposedPerson(Long partyId, Long politicallyExposedPersonId, PoliticallyExposedPersonDTO politicallyExposedPersonDTO);
    
    /**
     * Deletes a politically exposed person identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the politically exposed person
     * @param politicallyExposedPersonId the unique identifier of the politically exposed person to be deleted
     * @return a Mono that completes when the politically exposed person is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePoliticallyExposedPerson(Long partyId, Long politicallyExposedPersonId);
    
    /**
     * Retrieves a politically exposed person by its unique identifier for a specific party.
     *
     * @param partyId the unique identifier of the party that owns the politically exposed person
     * @param politicallyExposedPersonId the unique identifier of the politically exposed person to retrieve
     * @return a Mono emitting the {@link PoliticallyExposedPersonDTO} representing the politically exposed person if found,
     *         or an empty Mono if the politically exposed person does not exist
     */
    Mono<PoliticallyExposedPersonDTO> getPoliticallyExposedPersonById(Long partyId, Long politicallyExposedPersonId);
}