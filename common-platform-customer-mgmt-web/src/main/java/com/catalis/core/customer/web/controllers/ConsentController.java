package com.catalis.core.customer.web.controllers;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.services.ConsentService;
import com.catalis.core.customer.interfaces.dtos.ConsentDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/consents")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Consents", 
    description = "API for managing consents associated with parties - privacy and data processing agreements"
)
public class ConsentController {

    private final ConsentService consentService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter consents for a party",
        description = "Retrieve a paginated list of consents associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered consents",
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
    public Mono<ResponseEntity<PaginationResponse<ConsentDTO>>> filterConsents(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Filter criteria for consents", required = true)
            @Valid @RequestBody FilterRequest<ConsentDTO> filterRequest) {
        return consentService.filterConsents(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create consent for a party",
        description = "Create a new consent record associated with a specific party for data processing or privacy agreement"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Consent successfully created",
            content = @Content(schema = @Schema(implementation = ConsentDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid consent data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<ConsentDTO>> createConsent(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Consent data to create", required = true)
            @Valid @RequestBody ConsentDTO consentDTO) {
        return consentService.createConsent(partyId, consentDTO)
                .map(consent -> ResponseEntity.status(HttpStatus.CREATED).body(consent));
    }

    @GetMapping("/{consentId}")
    @Operation(
        summary = "Get consent by ID",
        description = "Retrieve a specific consent associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Consent found",
            content = @Content(schema = @Schema(implementation = ConsentDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Consent or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<ConsentDTO>> getConsentById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the consent", required = true)
            @PathVariable Long consentId) {
        return consentService.getConsentById(partyId, consentId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{consentId}")
    @Operation(
        summary = "Update consent",
        description = "Update an existing consent associated with a party - typically used for withdrawing or modifying consent"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Consent successfully updated",
            content = @Content(schema = @Schema(implementation = ConsentDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid consent data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Consent or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<ConsentDTO>> updateConsent(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the consent", required = true)
            @PathVariable Long consentId,
            @Parameter(description = "Updated consent data", required = true)
            @Valid @RequestBody ConsentDTO consentDTO) {
        return consentService.updateConsent(partyId, consentId, consentDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{consentId}")
    @Operation(
        summary = "Delete consent",
        description = "Delete a consent record associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Consent successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Consent or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deleteConsent(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable Long partyId,
            @Parameter(description = "Unique identifier of the consent", required = true)
            @PathVariable Long consentId) {
        return consentService.deleteConsent(partyId, consentId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}