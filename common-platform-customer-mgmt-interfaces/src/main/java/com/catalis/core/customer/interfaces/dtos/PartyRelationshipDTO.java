package com.catalis.core.customer.interfaces.dtos;

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
 * Data Transfer Object for Party Relationship entity representing relationships between parties.
 * Used for transferring party relationship data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyRelationshipDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long partyRelationshipId;

    @FilterableId
    @NotNull(message = "From party ID is required")
    private Long fromPartyId;

    @FilterableId
    @NotNull(message = "To party ID is required")
    private Long toPartyId;

    @FilterableId
    @NotNull(message = "Relationship type ID is required")
    private Long relationshipTypeId;
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}