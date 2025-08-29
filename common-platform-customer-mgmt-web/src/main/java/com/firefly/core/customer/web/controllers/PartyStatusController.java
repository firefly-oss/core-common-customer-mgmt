package com.firefly.core.customer.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.services.PartyStatusService;
import com.firefly.core.customer.interfaces.dtos.PartyStatusDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/party-statuses")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Party Statuses", 
    description = "API for managing party statuses associated with parties"
)
public class PartyStatusController {

    private final PartyStatusService partyStatusService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter party statuses for a party",
        description = "Retrieve a paginated list of party statuses associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered party statuses",
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
    public Mono<ResponseEntity<PaginationResponse<PartyStatusDTO>>> filterPartyStatuses(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Filter criteria for party statuses", required = true)
            @Valid @RequestBody FilterRequest<PartyStatusDTO> filterRequest) {
        return partyStatusService.filterPartyStatuses(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create party status for a party",
        description = "Create a new party status associated with a specific party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Party status successfully created",
            content = @Content(schema = @Schema(implementation = PartyStatusDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party status data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyStatusDTO>> createPartyStatus(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Party status data to create", required = true)
            @Valid @RequestBody PartyStatusDTO partyStatusDTO) {
        return partyStatusService.createPartyStatus(partyId, partyStatusDTO)
                .map(partyStatus -> ResponseEntity.status(HttpStatus.CREATED).body(partyStatus));
    }

    @GetMapping("/{partyStatusId}")
    @Operation(
        summary = "Get party status by ID",
        description = "Retrieve a specific party status associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party status found",
            content = @Content(schema = @Schema(implementation = PartyStatusDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party status or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyStatusDTO>> getPartyStatusById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the party status", required = true)
            @PathVariable Long partyStatusId) {
        return partyStatusService.getPartyStatusById(partyId, partyStatusId)
                .map(ResponseEntity::ok);
    }

    @PutMapping
    @Operation(
        summary = "Update party status",
        description = "Update an existing party status associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party status successfully updated",
            content = @Content(schema = @Schema(implementation = PartyStatusDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party status data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party status or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyStatusDTO>> updatePartyStatus(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Updated party status data", required = true)
            @Valid @RequestBody PartyStatusDTO partyStatusDTO) {
        return partyStatusService.updatePartyStatus(partyId, partyStatusDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{partyStatusId}")
    @Operation(
        summary = "Delete party status",
        description = "Delete a party status associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Party status successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party status or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deletePartyStatus(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the party status", required = true)
            @PathVariable Long partyStatusId) {
        return partyStatusService.deletePartyStatus(partyId, partyStatusId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}