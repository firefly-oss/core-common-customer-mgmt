package com.catalis.core.customer.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.services.LegalEntityService;
import com.catalis.core.customer.interfaces.dtos.LegalEntityDTO;
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

@RestController
@RequestMapping("/api/v1/parties/{partyId}/legal-entities")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Legal Entities", 
    description = "API for managing legal entities - corporate customers associated with parties"
)
public class LegalEntityController {

    private final LegalEntityService legalEntityService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter legal entities for a party",
        description = "Retrieve a paginated list of legal entities associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered legal entities",
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
    public Mono<ResponseEntity<PaginationResponse<LegalEntityDTO>>> filterLegalEntities(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Filter criteria for legal entities", required = true)
            @Valid @RequestBody FilterRequest<LegalEntityDTO> filterRequest) {
        return legalEntityService.filterLegalEntities(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create legal entity for a party",
        description = "Create a new legal entity associated with a specific party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Legal entity successfully created",
            content = @Content(schema = @Schema(implementation = LegalEntityDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid legal entity data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<LegalEntityDTO>> createLegalEntity(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Legal entity data to create", required = true)
            @Valid @RequestBody LegalEntityDTO legalEntityDTO) {
        return legalEntityService.createLegalEntity(partyId, legalEntityDTO)
                .map(legalEntity -> ResponseEntity.status(HttpStatus.CREATED).body(legalEntity));
    }

    @GetMapping("/{legalEntityId}")
    @Operation(
        summary = "Get legal entity by ID",
        description = "Retrieve a specific legal entity associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Legal entity found",
            content = @Content(schema = @Schema(implementation = LegalEntityDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Legal entity or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<LegalEntityDTO>> getLegalEntityById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the legal entity", required = true)
            @PathVariable Long legalEntityId) {
        return legalEntityService.getLegalEntityById(partyId, legalEntityId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{legalEntityId}")
    @Operation(
        summary = "Update legal entity",
        description = "Update an existing legal entity associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Legal entity successfully updated",
            content = @Content(schema = @Schema(implementation = LegalEntityDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid legal entity data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Legal entity or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<LegalEntityDTO>> updateLegalEntity(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the legal entity", required = true)
            @PathVariable Long legalEntityId,
            @Parameter(description = "Updated legal entity data", required = true)
            @Valid @RequestBody LegalEntityDTO legalEntityDTO) {
        return legalEntityService.updateLegalEntity(partyId, legalEntityId, legalEntityDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{legalEntityId}")
    @Operation(
        summary = "Delete legal entity",
        description = "Delete a legal entity associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Legal entity successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Legal entity or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deleteLegalEntity(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the legal entity", required = true)
            @PathVariable Long legalEntityId) {
        return legalEntityService.deleteLegalEntity(partyId, legalEntityId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}