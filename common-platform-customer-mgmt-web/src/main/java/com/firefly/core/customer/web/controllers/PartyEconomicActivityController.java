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
import com.firefly.core.customer.core.services.PartyEconomicActivityService;
import com.firefly.core.customer.interfaces.dtos.PartyEconomicActivityDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/party-economic-activities")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Party Economic Activities", 
    description = "API for managing party economic activities associated with parties"
)
public class PartyEconomicActivityController {

    private final PartyEconomicActivityService partyEconomicActivityService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter party economic activities for a party",
        description = "Retrieve a paginated list of party economic activities associated with a specific party based on filtering criteria"
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
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PaginationResponse<PartyEconomicActivityDTO>>> filterPartyEconomicActivities(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Filter criteria for party economic activities", required = true)
            @Valid @RequestBody FilterRequest<PartyEconomicActivityDTO> filterRequest) {
        return partyEconomicActivityService.filterPartyEconomicActivities(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create party economic activity for a party",
        description = "Create a new party economic activity associated with a specific party"
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
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyEconomicActivityDTO>> createPartyEconomicActivity(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Party economic activity data to create", required = true)
            @Valid @RequestBody PartyEconomicActivityDTO partyEconomicActivityDTO) {
        return partyEconomicActivityService.createPartyEconomicActivity(partyId, partyEconomicActivityDTO)
                .map(partyEconomicActivity -> ResponseEntity.status(HttpStatus.CREATED).body(partyEconomicActivity));
    }

    @GetMapping("/{partyEconomicActivityId}")
    @Operation(
        summary = "Get party economic activity by ID",
        description = "Retrieve a specific party economic activity associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Party economic activity found",
            content = @Content(schema = @Schema(implementation = PartyEconomicActivityDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party economic activity or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyEconomicActivityDTO>> getPartyEconomicActivityById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the party economic activity", required = true)
            @PathVariable UUID partyEconomicActivityId) {
        return partyEconomicActivityService.getPartyEconomicActivityById(partyId, partyEconomicActivityId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{partyEconomicActivityId}")
    @Operation(
        summary = "Update party economic activity",
        description = "Update an existing party economic activity associated with a party"
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
            description = "Party economic activity or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PartyEconomicActivityDTO>> updatePartyEconomicActivity(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the party economic activity", required = true)
            @PathVariable UUID partyEconomicActivityId,
            @Parameter(description = "Updated party economic activity data", required = true)
            @Valid @RequestBody PartyEconomicActivityDTO partyEconomicActivityDTO) {
        return partyEconomicActivityService.updatePartyEconomicActivity(partyId, partyEconomicActivityId, partyEconomicActivityDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{partyEconomicActivityId}")
    @Operation(
        summary = "Delete party economic activity",
        description = "Delete a party economic activity associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Party economic activity successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party economic activity or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deletePartyEconomicActivity(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the party economic activity", required = true)
            @PathVariable UUID partyEconomicActivityId) {
        return partyEconomicActivityService.deletePartyEconomicActivity(partyId, partyEconomicActivityId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}