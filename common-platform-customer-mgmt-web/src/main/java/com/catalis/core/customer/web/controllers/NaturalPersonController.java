package com.catalis.core.customer.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.services.NaturalPersonService;
import com.catalis.core.customer.interfaces.dtos.NaturalPersonDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/natural-persons")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Natural Persons", 
    description = "API for managing natural persons - individual customers associated with parties"
)
public class NaturalPersonController {

    private final NaturalPersonService naturalPersonService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter natural persons for a party",
        description = "Retrieve a paginated list of natural persons associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered natural persons",
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
    public Mono<ResponseEntity<PaginationResponse<NaturalPersonDTO>>> filterNaturalPersons(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Filter criteria for natural persons", required = true)
            @Valid @RequestBody FilterRequest<NaturalPersonDTO> filterRequest) {
        return naturalPersonService.filterNaturalPersons(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create natural person for a party",
        description = "Create a new natural person associated with a specific party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Natural person successfully created",
            content = @Content(schema = @Schema(implementation = NaturalPersonDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid natural person data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<NaturalPersonDTO>> createNaturalPerson(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Natural person data to create", required = true)
            @Valid @RequestBody NaturalPersonDTO naturalPersonDTO) {
        return naturalPersonService.createNaturalPerson(partyId, naturalPersonDTO)
                .map(naturalPerson -> ResponseEntity.status(HttpStatus.CREATED).body(naturalPerson));
    }

    @GetMapping("/{naturalPersonId}")
    @Operation(
        summary = "Get natural person by ID",
        description = "Retrieve a specific natural person associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Natural person found",
            content = @Content(schema = @Schema(implementation = NaturalPersonDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Natural person or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<NaturalPersonDTO>> getNaturalPersonById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the natural person", required = true)
            @PathVariable Long naturalPersonId) {
        return naturalPersonService.getNaturalPersonById(partyId, naturalPersonId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{naturalPersonId}")
    @Operation(
        summary = "Update natural person",
        description = "Update an existing natural person associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Natural person successfully updated",
            content = @Content(schema = @Schema(implementation = NaturalPersonDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid natural person data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Natural person or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<NaturalPersonDTO>> updateNaturalPerson(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the natural person", required = true)
            @PathVariable Long naturalPersonId,
            @Parameter(description = "Updated natural person data", required = true)
            @Valid @RequestBody NaturalPersonDTO naturalPersonDTO) {
        return naturalPersonService.updateNaturalPerson(partyId, naturalPersonId, naturalPersonDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{naturalPersonId}")
    @Operation(
        summary = "Delete natural person",
        description = "Delete a natural person associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Natural person successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Natural person or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deleteNaturalPerson(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the natural person", required = true)
            @PathVariable Long naturalPersonId) {
        return naturalPersonService.deleteNaturalPerson(partyId, naturalPersonId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}