/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.customer.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.customer.interfaces.dtos.AddressDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing addresses.
 */
public interface AddressService {
    /**
     * Filters the addresses based on the given criteria for a specific party.
     *
     * @param partyId the unique identifier of the party owning the addresses
     * @param filterRequest the request object containing filtering criteria for AddressDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of addresses
     */
    Mono<PaginationResponse<AddressDTO>> filterAddresses(UUID partyId, FilterRequest<AddressDTO> filterRequest);
    
    /**
     * Creates a new address based on the provided information for a specific party.
     *
     * @param partyId the unique identifier of the party that will own the address
     * @param addressDTO the DTO object containing details of the address to be created
     * @return a Mono that emits the created AddressDTO object
     */
    Mono<AddressDTO> createAddress(UUID partyId, AddressDTO addressDTO);
    
    /**
     * Updates an existing address with updated information, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the address
     * @param addressId the unique identifier of the address to be updated
     * @param addressDTO the data transfer object containing the updated details of the address
     * @return a reactive Mono containing the updated AddressDTO
     */
    Mono<AddressDTO> updateAddress(UUID partyId, UUID addressId, AddressDTO addressDTO);
    
    /**
     * Deletes an address identified by its unique ID, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the address
     * @param addressId the unique identifier of the address to be deleted
     * @return a Mono that completes when the address is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteAddress(UUID partyId, UUID addressId);
    
    /**
     * Retrieves an address by its unique identifier, validating party ownership.
     *
     * @param partyId the unique identifier of the party that owns the address
     * @param addressId the unique identifier of the address to retrieve
     * @return a Mono emitting the {@link AddressDTO} representing the address if found,
     *         or an empty Mono if the address does not exist or doesn't belong to the party
     */
    Mono<AddressDTO> getAddressById(UUID partyId, UUID addressId);
}