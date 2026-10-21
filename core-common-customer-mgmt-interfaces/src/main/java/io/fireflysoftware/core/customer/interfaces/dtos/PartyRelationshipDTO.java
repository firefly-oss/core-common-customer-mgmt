/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.customer.interfaces.dtos;

import io.fireflysoftware.core.customer.interfaces.enums.RelationshipKind;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
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
    @NotNull(message = "Source party ID is required")
    private UUID sourcePartyId;

    @FilterableId
    @NotNull(message = "Target party ID is required")
    private UUID targetPartyId;

    @NotNull(message = "Relationship kind is required")
    private RelationshipKind relationshipKind;

    @FilterableId
    private UUID relationshipTypeId;

    @DecimalMin(value = "0.0", inclusive = true, message = "Ownership percentage must be between 0 and 100")
    @DecimalMax(value = "100.0", inclusive = true, message = "Ownership percentage must be between 0 and 100")
    private BigDecimal ownershipPercentage;

    @FilterableId
    private UUID tenantId;

    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private Boolean active;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}