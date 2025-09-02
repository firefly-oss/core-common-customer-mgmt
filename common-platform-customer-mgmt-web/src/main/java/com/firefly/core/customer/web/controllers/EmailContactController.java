package com.firefly.core.customer.web.controllers;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.services.EmailContactService;
import com.firefly.core.customer.interfaces.dtos.EmailContactDTO;

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
@RequestMapping("/api/v1/parties/{partyId}/contacts/email")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Email Contacts", 
    description = "API for managing email contacts associated with parties"
)
public class EmailContactController {

    private final EmailContactService emailContactService;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter email contacts for a party",
        description = "Retrieve a paginated list of email contacts associated with a specific party based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved filtered email contacts",
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
    public Mono<ResponseEntity<PaginationResponse<EmailContactDTO>>> filterEmailContacts(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Filter criteria for email contacts", required = true)
            @Valid @RequestBody FilterRequest<EmailContactDTO> filterRequest) {
        return emailContactService.filterEmailContacts(partyId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create email contact for a party",
        description = "Create a new email contact associated with a specific party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Email contact successfully created",
            content = @Content(schema = @Schema(implementation = EmailContactDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid email contact data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<EmailContactDTO>> createEmailContact(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Email contact data to create", required = true)
            @Validated @RequestBody EmailContactDTO emailContactDTO) {
        return emailContactService.createEmailContact(partyId, emailContactDTO)
                .map(emailContact -> ResponseEntity.status(HttpStatus.CREATED).body(emailContact));
    }

    @GetMapping("/{emailContactId}")
    @Operation(
        summary = "Get email contact by ID",
        description = "Retrieve a specific email contact associated with a party by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Email contact found",
            content = @Content(schema = @Schema(implementation = EmailContactDTO.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Email contact or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<EmailContactDTO>> getEmailContactById(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the email contact", required = true)
            @PathVariable UUID emailContactId) {
        return emailContactService.getEmailContactById(partyId, emailContactId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{emailContactId}")
    @Operation(
        summary = "Update email contact",
        description = "Update an existing email contact associated with a party"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Email contact successfully updated",
            content = @Content(schema = @Schema(implementation = EmailContactDTO.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid email contact data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Email contact or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<EmailContactDTO>> updateEmailContact(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the email contact", required = true)
            @PathVariable UUID emailContactId,
            @Parameter(description = "Updated email contact data", required = true)
            @Validated @RequestBody EmailContactDTO emailContactDTO) {
        return emailContactService.updateEmailContact(partyId, emailContactId, emailContactDTO)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{emailContactId}")
    @Operation(
        summary = "Delete email contact",
        description = "Delete an email contact associated with a party from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204", 
            description = "Email contact successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Email contact or party not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> deleteEmailContact(
            @Parameter(description = "Unique identifier of the party", required = true)
            @PathVariable UUID partyId,
            @Parameter(description = "Unique identifier of the email contact", required = true)
            @PathVariable UUID emailContactId) {
        return emailContactService.deleteEmailContact(partyId, emailContactId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}