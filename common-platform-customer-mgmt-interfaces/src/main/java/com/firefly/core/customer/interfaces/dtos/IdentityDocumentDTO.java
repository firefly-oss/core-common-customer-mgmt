package com.firefly.core.customer.interfaces.dtos;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Identity Document entity representing identity documents for parties.
 * Used for transferring identity document data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityDocumentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID identityDocumentId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @FilterableId
    @NotNull(message = "Identity document category ID is required")
    private UUID identityDocumentCategoryId;

    @FilterableId
    @NotNull(message = "Identity document type ID is required")
    private UUID identityDocumentTypeId;

    @NotBlank(message = "Document number is required")
    @Size(max = 100, message = "Document number must not exceed 100 characters")
    private String documentNumber;

    @FilterableId
    @NotNull(message = "Issuing country ID is required")
    private UUID issuingCountryId;
    
    private LocalDateTime issueDate;
    
    @Future(message = "Expiry date must be in the future")
    private LocalDateTime expiryDate;
    
    @Size(max = 200, message = "Issuing authority must not exceed 200 characters")
    private String issuingAuthority;
    
    private Boolean validated;
    
    @Size(max = 500, message = "Document URI must not exceed 500 characters")
    private String documentUri;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}