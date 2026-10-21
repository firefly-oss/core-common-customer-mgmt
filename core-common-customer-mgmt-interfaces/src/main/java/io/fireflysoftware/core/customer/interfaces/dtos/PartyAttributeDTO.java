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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.fireflysoftware.core.customer.interfaces.enums.PartyAttributeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Effective-dated fact stored for any party, including employment, housing, and debts.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyAttributeDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID partyAttributeId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotBlank(message = "Attribute key is required")
    @Size(max = 100, message = "Attribute key must not exceed 100 characters")
    private String attributeKey;

    @NotNull(message = "Attribute type is required")
    private PartyAttributeType attributeType;

    @NotBlank(message = "Attribute value is required")
    private String attributeValue;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter ISO code")
    private String currencyCode;

    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
