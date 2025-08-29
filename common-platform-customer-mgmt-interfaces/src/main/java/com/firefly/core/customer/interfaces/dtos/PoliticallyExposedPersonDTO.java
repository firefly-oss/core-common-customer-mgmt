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

/**
 * Data Transfer Object for Politically Exposed Person entity representing PEP information for parties.
 * Used for transferring politically exposed person data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliticallyExposedPersonDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long pepId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private Long partyId;

    @NotNull(message = "PEP status is required")
    private Boolean pep;
    
    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;
    
    @Size(max = 200, message = "Public position must not exceed 200 characters")
    private String publicPosition;

    @FilterableId
    private Long countryOfPositionId;
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}