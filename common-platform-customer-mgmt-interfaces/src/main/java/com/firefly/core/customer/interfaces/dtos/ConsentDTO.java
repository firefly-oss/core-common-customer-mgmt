package com.firefly.core.customer.interfaces.dtos;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Consent entity representing user consent records for parties.
 * Used for transferring consent data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID consentId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @FilterableId
    @NotNull(message = "Consent type ID is required")
    private UUID consentTypeId;
    
    @NotNull(message = "Granted status is required")
    private Boolean granted;
    
    private LocalDateTime grantedAt;
    private LocalDateTime revokedAt;
    
    @Size(max = 50, message = "Channel must not exceed 50 characters")
    private String channel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}