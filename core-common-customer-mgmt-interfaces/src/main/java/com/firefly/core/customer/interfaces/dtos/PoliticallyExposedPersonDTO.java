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

import org.fireflyframework.utils.annotations.FilterableId;
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
 * Data Transfer Object for Politically Exposed Person entity representing PEP information for parties.
 * Used for transferring politically exposed person data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoliticallyExposedPersonDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID pepId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotNull(message = "PEP status is required")
    private Boolean pep;
    
    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;
    
    @Size(max = 200, message = "Public position must not exceed 200 characters")
    private String publicPosition;

    @FilterableId
    private UUID countryOfPositionId;
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}