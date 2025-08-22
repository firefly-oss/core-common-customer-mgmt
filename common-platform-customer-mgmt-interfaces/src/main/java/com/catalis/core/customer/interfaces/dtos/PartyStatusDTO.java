package com.catalis.core.customer.interfaces.dtos;

import com.catalis.core.customer.interfaces.enums.StatusCode;
import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

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
    private Long partyStatusId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private Long partyId;
    
    @NotNull(message = "Status code is required")
    private StatusCode statusCode;
    
    @Size(max = 500, message = "Status reason must not exceed 500 characters")
    private String statusReason;
    
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}