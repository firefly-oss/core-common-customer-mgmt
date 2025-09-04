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
import com.firefly.core.customer.core.services.IdentityDocumentService;
import com.firefly.core.customer.interfaces.dtos.IdentityDocumentDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/documents/identity")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Identity Documents", 
    description = "API for managing identity documents associated with parties"
)
public class IdentityDocumentController {

    private final IdentityDocumentService identityDocumentService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter identity documents for a party",
        description = "Retrieve a paginated list of identity documents associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered identity documents",
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
    public Mono<ResponseEntity<PaginationResponse<IdentityDocumentDTO>>> filterIdentityDocuments(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Filter criteria for identity documents", required = true)
            @Valid @RequestBody FilterRequest<IdentityDocumentDTO> filterRequest) {
        return identityDocumentService.filterIdentityDocuments(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create identity document for a party",
        description = "Create a new identity document associated with a specific party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Identity document successfully created",
            content = @Content(schema = @Schema(implementation = IdentityDocumentDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid identity document data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<IdentityDocumentDTO>> createIdentityDocument(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Identity document data to create", required = true)
            @Valid @RequestBody IdentityDocumentDTO identityDocumentDTO) {
        return identityDocumentService.createIdentityDocument(partyId, identityDocumentDTO)
                .map(document -> ResponseEntity.status(HttpStatus.CREATED).body(document));
    }

    @GetMapping("/{identityDocumentId}")
    @Operation(
        summary = "Get identity document by ID",
        description = "Retrieve a specific identity document associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Identity document found",
            content = @Content(schema = @Schema(implementation = IdentityDocumentDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Identity document or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<IdentityDocumentDTO>> getIdentityDocumentById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the identity document", required = true)
            @PathVariable UUID identityDocumentId) {
        return identityDocumentService.getIdentityDocumentById(partyId, identityDocumentId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{identityDocumentId}")
    @Operation(
        summary = "Update identity document",
        description = "Update an existing identity document associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Identity document successfully updated",
            content = @Content(schema = @Schema(implementation = IdentityDocumentDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid identity document data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Identity document or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<IdentityDocumentDTO>> updateIdentityDocument(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the identity document", required = true)
            @PathVariable UUID identityDocumentId,
            @Parameter(description = "Updated identity document data", required = true)
            @Valid @RequestBody IdentityDocumentDTO identityDocumentDTO) {
        return identityDocumentService.updateIdentityDocument(partyId, identityDocumentId, identityDocumentDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{identityDocumentId}")
    @Operation(
        summary = "Delete identity document",
        description = "Delete an identity document associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Identity document successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Identity document or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deleteIdentityDocument(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the identity document", required = true)
            @PathVariable UUID identityDocumentId) {
        return identityDocumentService.deleteIdentityDocument(partyId, identityDocumentId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}