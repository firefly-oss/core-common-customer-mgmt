package com.firefly.core.customer.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.services.PoliticallyExposedPersonService;
import com.firefly.core.customer.interfaces.dtos.PoliticallyExposedPersonDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/politically-exposed-persons")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Politically Exposed Persons", 
    description = "API for managing politically exposed persons (PEP) associated with parties - regulatory compliance entities"
)
public class PoliticallyExposedPersonController {

    private final PoliticallyExposedPersonService politicallyExposedPersonService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter politically exposed persons for a party",
        description = "Retrieve a paginated list of politically exposed persons associated with a specific party based on filtering criteria for regulatory compliance"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered politically exposed persons",
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
    public Mono<ResponseEntity<PaginationResponse<PoliticallyExposedPersonDTO>>> filterPoliticallyExposedPersons(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Filter criteria for politically exposed persons", required = true)
            @Valid @RequestBody FilterRequest<PoliticallyExposedPersonDTO> filterRequest) {
        return politicallyExposedPersonService.filterPoliticallyExposedPersons(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create politically exposed person for a party",
        description = "Create a new politically exposed person record associated with a specific party for regulatory compliance"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Politically exposed person successfully created",
            content = @Content(schema = @Schema(implementation = PoliticallyExposedPersonDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid politically exposed person data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PoliticallyExposedPersonDTO>> createPoliticallyExposedPerson(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Politically exposed person data to create", required = true)
            @Valid @RequestBody PoliticallyExposedPersonDTO politicallyExposedPersonDTO) {
        return politicallyExposedPersonService.createPoliticallyExposedPerson(partyId, politicallyExposedPersonDTO)
                .map(pep -> ResponseEntity.status(HttpStatus.CREATED).body(pep));
    }

    @GetMapping("/{pepId}")
    @Operation(
        summary = "Get politically exposed person by ID",
        description = "Retrieve a specific politically exposed person associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Politically exposed person found",
            content = @Content(schema = @Schema(implementation = PoliticallyExposedPersonDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Politically exposed person or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PoliticallyExposedPersonDTO>> getPoliticallyExposedPersonById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the politically exposed person", required = true)
            @PathVariable Long pepId) {
        return politicallyExposedPersonService.getPoliticallyExposedPersonById(partyId, pepId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{pepId}")
    @Operation(
        summary = "Update politically exposed person",
        description = "Update an existing politically exposed person record associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Politically exposed person successfully updated",
            content = @Content(schema = @Schema(implementation = PoliticallyExposedPersonDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid politically exposed person data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Politically exposed person or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PoliticallyExposedPersonDTO>> updatePoliticallyExposedPerson(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the politically exposed person", required = true)
            @PathVariable Long pepId,
            @Parameter(description = "Updated politically exposed person data", required = true)
            @Valid @RequestBody PoliticallyExposedPersonDTO politicallyExposedPersonDTO) {
        return politicallyExposedPersonService.updatePoliticallyExposedPerson(partyId, pepId, politicallyExposedPersonDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{pepId}")
    @Operation(
        summary = "Delete politically exposed person",
        description = "Delete a politically exposed person record associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Politically exposed person successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Politically exposed person or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deletePoliticallyExposedPerson(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the politically exposed person", required = true)
            @PathVariable Long pepId) {
        return politicallyExposedPersonService.deletePoliticallyExposedPerson(partyId, pepId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}