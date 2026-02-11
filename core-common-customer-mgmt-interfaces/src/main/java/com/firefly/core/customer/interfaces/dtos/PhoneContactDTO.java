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

import com.firefly.core.customer.interfaces.enums.PhoneKind;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Phone Contact entity representing phone contact information for parties.
 * Used for transferring phone contact data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneContactDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID phoneContactId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[\\+]?[1-9]\\d{1,14}$", message = "Phone number must be a valid format")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotNull(message = "Phone kind is required")
    private PhoneKind phoneKind;
    
    private Boolean isPrimary;
    private Boolean isVerified;
    
    @Size(max = 10, message = "Extension must not exceed 10 characters")
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}