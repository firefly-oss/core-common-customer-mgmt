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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Identity Document entity representing identity documents for parties.
 * Used for transferring identity document data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityDocumentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID identityDocumentId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @FilterableId
    @NotNull(message = "Identity document category ID is required")
    private UUID identityDocumentCategoryId;

    @FilterableId
    @NotNull(message = "Identity document type ID is required")
    private UUID identityDocumentTypeId;

    @NotBlank(message = "Document number is required")
    @Size(max = 100, message = "Document number must not exceed 100 characters")
    private String documentNumber;

    @FilterableId
    @NotNull(message = "Issuing country ID is required")
    private UUID issuingCountryId;
    
    private LocalDateTime issueDate;
    
    @Future(message = "Expiry date must be in the future")
    private LocalDateTime expiryDate;
    
    @Size(max = 200, message = "Issuing authority must not exceed 200 characters")
    private String issuingAuthority;
    
    private Boolean validated;
    
    @Size(max = 500, message = "Document URI must not exceed 500 characters")
    private String documentUri;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}