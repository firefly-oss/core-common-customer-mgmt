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
import io.fireflysoftware.core.customer.interfaces.enums.IndustryClassificationScheme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Industry or activity classification for a party. Many rows are allowed; at most one is primary.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyIndustryClassificationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID partyIndustryClassificationId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotNull(message = "Classification scheme is required")
    private IndustryClassificationScheme classificationScheme;

    @NotBlank(message = "Classification code is required")
    @Size(max = 32, message = "Classification code must not exceed 32 characters")
    private String classificationCode;

    private Boolean isPrimary;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
