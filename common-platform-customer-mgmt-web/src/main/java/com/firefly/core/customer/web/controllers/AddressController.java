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


package com.firefly.core.customer.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.customer.core.services.AddressService;
import com.firefly.core.customer.interfaces.dtos.AddressDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parties/{partyId}/addresses")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Addresses", 
    description = "API for managing addresses associated with parties"
)
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter addresses for a party",
        description = "Retrieve a paginated list of addresses associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered addresses",
            content = @Content(schema = @Schema(implementation = PaginationResponse.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid filter request",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PaginationResponse<AddressDTO>>> filterAddresses(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Filter criteria for addresses", required = true)
            @Valid @RequestBody FilterRequest<AddressDTO> filterRequest) {
        return addressService.filterAddresses(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create address for a party",
        description = "Create a new address associated with a specific party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Address successfully created",
            content = @Content(schema = @Schema(implementation = AddressDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid address data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AddressDTO>> createAddress(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Address data to create", required = true)
            @Valid @RequestBody AddressDTO addressDTO) {
        return addressService.createAddress(partyId, addressDTO)
                .map(address -> ResponseEntity.status(HttpStatus.CREATED).body(address));
    }

    @GetMapping("/{addressId}")
    @Operation(
        summary = "Get address by ID",
        description = "Retrieve a specific address associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Address found",
            content = @Content(schema = @Schema(implementation = AddressDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Address or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AddressDTO>> getAddressById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the address", required = true)
            @PathVariable UUID addressId) {
        return addressService.getAddressById(partyId, addressId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{addressId}")
    @Operation(
        summary = "Update address",
        description = "Update an existing address associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Address successfully updated",
            content = @Content(schema = @Schema(implementation = AddressDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid address data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Address or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AddressDTO>> updateAddress(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the address", required = true)
            @PathVariable UUID addressId,
            @Parameter(description = "Updated address data", required = true)
            @Valid @RequestBody AddressDTO addressDTO) {
        return addressService.updateAddress(partyId, addressId, addressDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{addressId}")
    @Operation(
        summary = "Delete address",
        description = "Delete an address associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Address successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Address or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deleteAddress(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the address", required = true)
            @PathVariable UUID addressId) {
        return addressService.deleteAddress(partyId, addressId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}