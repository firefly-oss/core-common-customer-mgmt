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
 * Data Transfer Object for Party Relationship entity representing relationships between parties.
 * Used for transferring party relationship data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyRelationshipDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID partyRelationshipId;

    @FilterableId
    @NotNull(message = "From party ID is required")
    private UUID fromPartyId;

    @FilterableId
    @NotNull(message = "To party ID is required")
    private UUID toPartyId;

    @FilterableId
    @NotNull(message = "Relationship type ID is required")
    private UUID relationshipTypeId;
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}