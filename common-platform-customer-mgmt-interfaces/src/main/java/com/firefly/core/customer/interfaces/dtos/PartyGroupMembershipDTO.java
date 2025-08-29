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
 * Data Transfer Object for Party Group Membership entity representing group memberships for parties.
 * Used for transferring party group membership data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyGroupMembershipDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long partyGroupMembershipId;

    @FilterableId
    @NotNull(message = "Group ID is required")
    private Long groupId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private Long partyId;

    private Boolean isActive;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}