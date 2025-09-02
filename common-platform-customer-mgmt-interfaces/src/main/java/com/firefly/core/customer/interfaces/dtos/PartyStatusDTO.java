package com.firefly.core.customer.interfaces.dtos;

import com.firefly.core.customer.interfaces.enums.StatusCode;
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
 * Data Transfer Object for Party Status entity representing status history for parties.
 * Used for transferring party status data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyStatusDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID partyStatusId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;
    
    @NotNull(message = "Status code is required")
    private StatusCode statusCode;
    
    @Size(max = 500, message = "Status reason must not exceed 500 characters")
    private String statusReason;
    
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}