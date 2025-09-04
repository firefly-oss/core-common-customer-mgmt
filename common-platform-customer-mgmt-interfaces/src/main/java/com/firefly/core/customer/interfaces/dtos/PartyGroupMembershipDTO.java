/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
 * Data Transfer Object for Party Group Membership entity representing group memberships for parties.
 * Used for transferring party group membership data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyGroupMembershipDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID partyGroupMembershipId;

    @FilterableId
    @NotNull(message = "Group ID is required")
    private UUID groupId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    private Boolean isActive;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}