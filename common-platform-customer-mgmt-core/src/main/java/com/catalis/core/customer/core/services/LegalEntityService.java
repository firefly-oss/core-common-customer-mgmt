package com.catalis.core.customer.core.services;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.interfaces.dtos.LegalEntityDTO;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing legal entities.
 */
public interface LegalEntityService {
    /**
     * Filters the legal entities based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for LegalEntityDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of legal entities
     */
    Mono<PaginationResponse<LegalEntityDTO>> filterLegalEntities(FilterRequest<LegalEntityDTO> filterRequest);
    
    /**
     * Creates a new legal entity based on the provided information.
     *
     * @param legalEntityDTO the DTO object containing details of the legal entity to be created
     * @return a Mono that emits the created LegalEntityDTO object
     */
    Mono<LegalEntityDTO> createLegalEntity(LegalEntityDTO legalEntityDTO);
    
    /**
     * Updates an existing legal entity with updated information.
     *
     * @param legalEntityId the unique identifier of the legal entity to be updated
     * @param legalEntityDTO the data transfer object containing the updated details of the legal entity
     * @return a reactive Mono containing the updated LegalEntityDTO
     */
    Mono<LegalEntityDTO> updateLegalEntity(Long legalEntityId, LegalEntityDTO legalEntityDTO);
    
    /**
     * Deletes a legal entity identified by its unique ID.
     *
     * @param legalEntityId the unique identifier of the legal entity to be deleted
     * @return a Mono that completes when the legal entity is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteLegalEntity(Long legalEntityId);
    
    /**
     * Retrieves a legal entity by its unique identifier.
     *
     * @param legalEntityId the unique identifier of the legal entity to retrieve
     * @return a Mono emitting the {@link LegalEntityDTO} representing the legal entity if found,
     *         or an empty Mono if the legal entity does not exist
     */
    Mono<LegalEntityDTO> getLegalEntityById(Long legalEntityId);
}