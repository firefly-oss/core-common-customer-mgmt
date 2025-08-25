package com.catalis.core.customer.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.services.PartyRelationshipService;
import com.catalis.core.customer.interfaces.dtos.PartyRelationshipDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/relationships")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Party Relationships", 
    description = "API for managing relationships between parties - hierarchies, associations, and business connections"
)
public class PartyRelationshipController {

    private final PartyRelationshipService partyRelationshipService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter party relationships",
        description = "Retrieve a paginated list of relationships associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered party relationships",
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
    public Mono<ResponseEntity<PaginationResponse<PartyRelationshipDTO>>> filterPartyRelationships(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Filter criteria for party relationships", required = true)
            @Valid @RequestBody FilterRequest<PartyRelationshipDTO> filterRequest) {
        return partyRelationshipService.filterPartyRelationships(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create party relationship",
        description = "Create a new relationship between the specified party and another party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Party relationship successfully created",
            content = @Content(schema = @Schema(implementation = PartyRelationshipDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party relationship data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyRelationshipDTO>> createPartyRelationship(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Party relationship data to create", required = true)
            @Valid @RequestBody PartyRelationshipDTO partyRelationshipDTO) {
        return partyRelationshipService.createPartyRelationship(partyId, partyRelationshipDTO)
                .map(relationship -> ResponseEntity.status(HttpStatus.CREATED).body(relationship));
    }

    @GetMapping("/{relationshipId}")
    @Operation(
        summary = "Get party relationship by ID",
        description = "Retrieve a specific relationship associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party relationship found",
            content = @Content(schema = @Schema(implementation = PartyRelationshipDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party relationship or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyRelationshipDTO>> getPartyRelationshipById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the party relationship", required = true)
            @PathVariable Long relationshipId) {
        return partyRelationshipService.getPartyRelationshipById(partyId, relationshipId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{relationshipId}")
    @Operation(
        summary = "Update party relationship",
        description = "Update an existing relationship associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party relationship successfully updated",
            content = @Content(schema = @Schema(implementation = PartyRelationshipDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party relationship data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party relationship or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyRelationshipDTO>> updatePartyRelationship(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the party relationship", required = true)
            @PathVariable Long relationshipId,
            @Parameter(description = "Updated party relationship data", required = true)
            @Valid @RequestBody PartyRelationshipDTO partyRelationshipDTO) {
        return partyRelationshipService.updatePartyRelationship(partyId, relationshipId, partyRelationshipDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{relationshipId}")
    @Operation(
        summary = "Delete party relationship",
        description = "Delete a relationship associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Party relationship successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party relationship or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deletePartyRelationship(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the party relationship", required = true)
            @PathVariable Long relationshipId) {
        return partyRelationshipService.deletePartyRelationship(partyId, relationshipId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}