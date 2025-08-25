package com.catalis.core.customer.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.services.PartyProviderService;
import com.catalis.core.customer.interfaces.dtos.PartyProviderDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/party-providers")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Party Providers", 
    description = "API for managing party providers associated with parties"
)
public class PartyProviderController {

    private final PartyProviderService partyProviderService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter party providers for a party",
        description = "Retrieve a paginated list of party providers associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered party providers",
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
    public Mono<ResponseEntity<PaginationResponse<PartyProviderDTO>>> filterPartyProviders(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Filter criteria for party providers", required = true)
            @Valid @RequestBody FilterRequest<PartyProviderDTO> filterRequest) {
        return partyProviderService.filterPartyProviders(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create party provider for a party",
        description = "Create a new party provider associated with a specific party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Party provider successfully created",
            content = @Content(schema = @Schema(implementation = PartyProviderDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party provider data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyProviderDTO>> createPartyProvider(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Party provider data to create", required = true)
            @Valid @RequestBody PartyProviderDTO partyProviderDTO) {
        return partyProviderService.createPartyProvider(partyId, partyProviderDTO)
                .map(partyProvider -> ResponseEntity.status(HttpStatus.CREATED).body(partyProvider));
    }

    @GetMapping("/{partyProviderId}")
    @Operation(
        summary = "Get party provider by ID",
        description = "Retrieve a specific party provider associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party provider found",
            content = @Content(schema = @Schema(implementation = PartyProviderDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party provider or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyProviderDTO>> getPartyProviderById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the party provider", required = true)
            @PathVariable Long partyProviderId) {
        return partyProviderService.getPartyProviderById(partyId, partyProviderId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{partyProviderId}")
    @Operation(
        summary = "Update party provider",
        description = "Update an existing party provider associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party provider successfully updated",
            content = @Content(schema = @Schema(implementation = PartyProviderDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party provider data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party provider or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyProviderDTO>> updatePartyProvider(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the party provider", required = true)
            @PathVariable Long partyProviderId,
            @Parameter(description = "Updated party provider data", required = true)
            @Valid @RequestBody PartyProviderDTO partyProviderDTO) {
        return partyProviderService.updatePartyProvider(partyId, partyProviderId, partyProviderDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{partyProviderId}")
    @Operation(
        summary = "Delete party provider",
        description = "Delete a party provider associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Party provider successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party provider or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deletePartyProvider(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the party provider", required = true)
            @PathVariable Long partyProviderId) {
        return partyProviderService.deletePartyProvider(partyId, partyProviderId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}