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

import com.firefly.core.customer.interfaces.enums.ProviderStatus;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Party Provider entity representing external system mappings for parties.
 * Used for transferring party provider data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyProviderDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID partyProviderId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;
    
    @NotBlank(message = "Provider name is required")
    @Size(max = 100, message = "Provider name must not exceed 100 characters")
    private String providerName;
    
    @NotBlank(message = "External reference is required")
    @Size(max = 200, message = "External reference must not exceed 200 characters")
    private String externalReference;
    
    @NotNull(message = "Provider status is required")
    private ProviderStatus providerStatus;
    
    private LocalDateTime lastSyncAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}