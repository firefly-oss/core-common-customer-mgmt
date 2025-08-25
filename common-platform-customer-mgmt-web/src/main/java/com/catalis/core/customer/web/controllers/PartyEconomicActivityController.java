package com.catalis.core.customer.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.services.PartyEconomicActivityService;
import com.catalis.core.customer.interfaces.dtos.PartyEconomicActivityDTO;
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
@RequestMapping("/api/v1/party-economic-activities")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Party Economic Activities", 
    description = "API for managing party economic activities"
)
public class PartyEconomicActivityController {

    private final PartyEconomicActivityService partyEconomicActivityService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter party economic activities",
        description = "Retrieve a paginated list of party economic activities based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered party economic activities",
            content = @Content(schema = @Schema(implementation = PaginationResponse.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid filter request",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PaginationResponse<PartyEconomicActivityDTO>>> filterPartyEconomicActivities(
            @Parameter(description = "Filter criteria for party economic activities", required = true)
            @Valid @RequestBody FilterRequest<PartyEconomicActivityDTO> filterRequest) {
        return partyEconomicActivityService.filterPartyEconomicActivities(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create party economic activity",
        description = "Create a new party economic activity"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Party economic activity successfully created",
            content = @Content(schema = @Schema(implementation = PartyEconomicActivityDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party economic activity data",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyEconomicActivityDTO>> createPartyEconomicActivity(
            @Parameter(description = "Party economic activity data to create", required = true)
            @Valid @RequestBody PartyEconomicActivityDTO partyEconomicActivityDTO) {
        return partyEconomicActivityService.createPartyEconomicActivity(partyEconomicActivityDTO)
                .map(partyEconomicActivity -> ResponseEntity.status(HttpStatus.CREATED).body(partyEconomicActivity));
    }

    @GetMapping("/{partyEconomicActivityId}")
    @Operation(
        summary = "Get party economic activity by ID",
        description = "Retrieve a specific party economic activity by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party economic activity found",
            content = @Content(schema = @Schema(implementation = PartyEconomicActivityDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party economic activity not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyEconomicActivityDTO>> getPartyEconomicActivityById(
            @Parameter(description = "Unique identifier of the party economic activity", required = true)
            @PathVariable Long partyEconomicActivityId) {
        return partyEconomicActivityService.getPartyEconomicActivityById(partyEconomicActivityId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{partyEconomicActivityId}")
    @Operation(
        summary = "Update party economic activity",
        description = "Update an existing party economic activity"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party economic activity successfully updated",
            content = @Content(schema = @Schema(implementation = PartyEconomicActivityDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid party economic activity data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party economic activity not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyEconomicActivityDTO>> updatePartyEconomicActivity(
            @Parameter(description = "Unique identifier of the party economic activity", required = true)
            @PathVariable Long partyEconomicActivityId,
            @Parameter(description = "Updated party economic activity data", required = true)
            @Valid @RequestBody PartyEconomicActivityDTO partyEconomicActivityDTO) {
        return partyEconomicActivityService.updatePartyEconomicActivity(partyEconomicActivityId, partyEconomicActivityDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{partyEconomicActivityId}")
    @Operation(
        summary = "Delete party economic activity",
        description = "Delete a party economic activity from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Party economic activity successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party economic activity not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deletePartyEconomicActivity(
            @Parameter(description = "Unique identifier of the party economic activity", required = true)
            @PathVariable Long partyEconomicActivityId) {
        return partyEconomicActivityService.deletePartyEconomicActivity(partyEconomicActivityId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}