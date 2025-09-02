package com.firefly.core.customer.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.services.PartyGroupMembershipService;
import com.firefly.core.customer.interfaces.dtos.PartyGroupMembershipDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/party-group-memberships")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Party Group Memberships", 
    description = "API for managing party group memberships associated with parties"
)
public class PartyGroupMembershipController {

    private final PartyGroupMembershipService partyGroupMembershipService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter party group memberships for a party",
        description = "Retrieve a paginated list of party group memberships associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered party group memberships",
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
    public Mono<ResponseEntity<PaginationResponse<PartyGroupMembershipDTO>>> filterPartyGroupMemberships(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Filter criteria for party group memberships", required = true)
            @Valid @RequestBody FilterRequest<PartyGroupMembershipDTO> filterRequest) {
        return partyGroupMembershipService.filterPartyGroupMemberships(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create party group membership for a party",
        description = "Create a new party group membership associated with a specific party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Party group membership successfully created",
            content = @Content(schema = @Schema(implementation = PartyGroupMembershipDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party group membership data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyGroupMembershipDTO>> createPartyGroupMembership(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Party group membership data to create", required = true)
            @Valid @RequestBody PartyGroupMembershipDTO partyGroupMembershipDTO) {
        return partyGroupMembershipService.createPartyGroupMembership(partyId, partyGroupMembershipDTO)
                .map(partyGroupMembership -> ResponseEntity.status(HttpStatus.CREATED).body(partyGroupMembership));
    }

    @GetMapping("/{partyGroupMembershipId}")
    @Operation(
        summary = "Get party group membership by ID",
        description = "Retrieve a specific party group membership associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party group membership found",
            content = @Content(schema = @Schema(implementation = PartyGroupMembershipDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party group membership or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyGroupMembershipDTO>> getPartyGroupMembershipById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the party group membership", required = true)
            @PathVariable UUID partyGroupMembershipId) {
        return partyGroupMembershipService.getPartyGroupMembershipById(partyId, partyGroupMembershipId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{partyGroupMembershipId}")
    @Operation(
        summary = "Update party group membership",
        description = "Update an existing party group membership associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party group membership successfully updated",
            content = @Content(schema = @Schema(implementation = PartyGroupMembershipDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party group membership data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party group membership or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyGroupMembershipDTO>> updatePartyGroupMembership(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the party group membership", required = true)
            @PathVariable UUID partyGroupMembershipId,
            @Parameter(description = "Updated party group membership data", required = true)
            @Valid @RequestBody PartyGroupMembershipDTO partyGroupMembershipDTO) {
        return partyGroupMembershipService.updatePartyGroupMembership(partyId, partyGroupMembershipId, partyGroupMembershipDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{partyGroupMembershipId}")
    @Operation(
        summary = "Delete party group membership",
        description = "Delete a party group membership associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Party group membership successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party group membership or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deletePartyGroupMembership(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the party group membership", required = true)
            @PathVariable UUID partyGroupMembershipId) {
        return partyGroupMembershipService.deletePartyGroupMembership(partyId, partyGroupMembershipId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}