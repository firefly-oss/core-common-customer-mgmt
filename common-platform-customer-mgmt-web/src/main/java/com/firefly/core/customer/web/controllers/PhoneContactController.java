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

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.services.PhoneContactService;

import com.firefly.core.customer.interfaces.dtos.PhoneContactDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/contacts/phone")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Phone Contacts", 
    description = "API for managing phone contacts associated with parties"
)
public class PhoneContactController {

    private final PhoneContactService phoneContactService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter phone contacts for a party",
        description = "Retrieve a paginated list of phone contacts associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered phone contacts",
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
    public Mono<ResponseEntity<PaginationResponse<PhoneContactDTO>>> filterPhoneContacts(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Filter criteria for phone contacts", required = true)
            @Valid @RequestBody FilterRequest<PhoneContactDTO> filterRequest) {
        return phoneContactService.filterPhoneContacts(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create phone contact for a party",
        description = "Create a new phone contact associated with a specific party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Phone contact successfully created",
            content = @Content(schema = @Schema(implementation = PhoneContactDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid phone contact data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PhoneContactDTO>> createPhoneContact(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Phone contact data to create", required = true)
            @Validated @RequestBody PhoneContactDTO phoneContactDTO) {
        return phoneContactService.createPhoneContact(partyId, phoneContactDTO)
                .map(phoneContact -> ResponseEntity.status(HttpStatus.CREATED).body(phoneContact));
    }

    @GetMapping("/{phoneContactId}")
    @Operation(
        summary = "Get phone contact by ID",
        description = "Retrieve a specific phone contact associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Phone contact found",
            content = @Content(schema = @Schema(implementation = PhoneContactDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Phone contact or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PhoneContactDTO>> getPhoneContactById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the phone contact", required = true)
            @PathVariable UUID phoneContactId) {
        return phoneContactService.getPhoneContactById(partyId, phoneContactId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{phoneContactId}")
    @Operation(
        summary = "Update phone contact",
        description = "Update an existing phone contact associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Phone contact successfully updated",
            content = @Content(schema = @Schema(implementation = PhoneContactDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid phone contact data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Phone contact or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PhoneContactDTO>> updatePhoneContact(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the phone contact", required = true)
            @PathVariable UUID phoneContactId,
            @Parameter(description = "Updated phone contact data", required = true)
            @Validated @RequestBody PhoneContactDTO phoneContactDTO) {
        return phoneContactService.updatePhoneContact(partyId, phoneContactId, phoneContactDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{phoneContactId}")
    @Operation(
        summary = "Delete phone contact",
        description = "Delete a phone contact associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Phone contact successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Phone contact or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deletePhoneContact(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the phone contact", required = true)
            @PathVariable UUID phoneContactId) {
        return phoneContactService.deletePhoneContact(partyId, phoneContactId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}