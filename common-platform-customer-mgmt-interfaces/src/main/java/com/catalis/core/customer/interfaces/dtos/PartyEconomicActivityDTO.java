package com.catalis.core.customer.interfaces.dtos;

import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Party Economic Activity entity representing economic activities for parties.
 * Used for transferring party economic activity data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyEconomicActivityDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long partyEconomicActivityId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private Long partyId;

    @FilterableId
    @NotNull(message = "Economic activity ID is required")
    private Long economicActivityId;
    
    @DecimalMin(value = "0.0", inclusive = true, message = "Annual turnover must be non-negative")
    private BigDecimal annualTurnover;
    
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a valid 3-letter ISO code")
    @Size(max = 3, message = "Currency code must be 3 characters")
    private String currencyCode;
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isPrimary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}