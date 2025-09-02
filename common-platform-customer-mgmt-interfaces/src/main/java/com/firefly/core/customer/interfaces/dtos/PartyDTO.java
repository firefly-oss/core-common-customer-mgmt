package com.firefly.core.customer.interfaces.dtos;

import com.firefly.core.customer.interfaces.enums.PartyKind;
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
 * Data Transfer Object for Party entity representing the supertype for all customer types.
 * Used for transferring party data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID partyId;
    
    @NotNull(message = "Party kind is required")
    private PartyKind partyKind;
    
    @Size(max = 10, message = "Preferred language must not exceed 10 characters")
    private String preferredLanguage;
    
    @Size(max = 100, message = "Source system must not exceed 100 characters")
    private String sourceSystem;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}