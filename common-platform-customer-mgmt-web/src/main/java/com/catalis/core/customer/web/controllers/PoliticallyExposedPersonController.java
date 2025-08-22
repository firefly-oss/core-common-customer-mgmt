package com.catalis.core.customer.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.services.PoliticallyExposedPersonService;
import com.catalis.core.customer.interfaces.dtos.PoliticallyExposedPersonDTO;
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
@RequestMapping("/api/v1/politically-exposed-persons")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Politically Exposed Persons", 
    description = "API for managing politically exposed persons (PEP) - regulatory compliance entities"
)
public class PoliticallyExposedPersonController {

    private final PoliticallyExposedPersonService politicallyExposedPersonService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter politically exposed persons",
        description = "Retrieve a paginated list of politically exposed persons based on filtering criteria for regulatory compliance"
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
        )
    })
    public Mono<ResponseEntity<PaginationResponse<PoliticallyExposedPersonDTO>>> filterPoliticallyExposedPersons(
            @Parameter(description = "Filter criteria for politically exposed persons", required = true)
            @Valid @RequestBody FilterRequest<PoliticallyExposedPersonDTO> filterRequest) {
        return politicallyExposedPersonService.filterPoliticallyExposedPersons(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create politically exposed person",
        description = "Create a new politically exposed person record for regulatory compliance"
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
        )
    })
    public Mono<ResponseEntity<PoliticallyExposedPersonDTO>> createPoliticallyExposedPerson(
            @Parameter(description = "Politically exposed person data to create", required = true)
            @Valid @RequestBody PoliticallyExposedPersonDTO politicallyExposedPersonDTO) {
        return politicallyExposedPersonService.createPoliticallyExposedPerson(politicallyExposedPersonDTO)
                .map(pep -> ResponseEntity.status(HttpStatus.CREATED).body(pep));
    }

    @GetMapping("/{pepId}")
    @Operation(
        summary = "Get politically exposed person by ID",
        description = "Retrieve a specific politically exposed person by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Politically exposed person found",
            content = @Content(schema = @Schema(implementation = PoliticallyExposedPersonDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Politically exposed person not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PoliticallyExposedPersonDTO>> getPoliticallyExposedPersonById(
            @Parameter(description = "Unique identifier of the politically exposed person", required = true)
            @PathVariable Long pepId) {
        return politicallyExposedPersonService.getPoliticallyExposedPersonById(pepId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{pepId}")
    @Operation(
        summary = "Update politically exposed person",
        description = "Update an existing politically exposed person record"
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
            description = "Politically exposed person not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PoliticallyExposedPersonDTO>> updatePoliticallyExposedPerson(
            @Parameter(description = "Unique identifier of the politically exposed person", required = true)
            @PathVariable Long pepId,
            @Parameter(description = "Updated politically exposed person data", required = true)
            @Valid @RequestBody PoliticallyExposedPersonDTO politicallyExposedPersonDTO) {
        return politicallyExposedPersonService.updatePoliticallyExposedPerson(pepId, politicallyExposedPersonDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{pepId}")
    @Operation(
        summary = "Delete politically exposed person",
        description = "Delete a politically exposed person record from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Politically exposed person successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Politically exposed person not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deletePoliticallyExposedPerson(
            @Parameter(description = "Unique identifier of the politically exposed person", required = true)
            @PathVariable Long pepId) {
        return politicallyExposedPersonService.deletePoliticallyExposedPerson(pepId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}