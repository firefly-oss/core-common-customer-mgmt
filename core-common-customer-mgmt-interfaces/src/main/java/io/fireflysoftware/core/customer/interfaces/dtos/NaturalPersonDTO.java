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

import io.fireflysoftware.core.customer.interfaces.enums.Gender;
import io.fireflysoftware.core.customer.interfaces.enums.MaritalStatus;
import io.fireflysoftware.core.customer.interfaces.enums.ResidencyStatus;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Natural Person entity representing individual customers.
 * Used for transferring natural person data between application layers.
 * This is a subtype of Party with a 1:1 relationship.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaturalPersonDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID naturalPersonId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @Size(max = 50, message = "Title must not exceed 50 characters")
    private String title;
    
    @NotBlank(message = "Given name is required")
    @Size(max = 100, message = "Given name must not exceed 100 characters")
    private String givenName;
    
    @Size(max = 100, message = "Middle name must not exceed 100 characters")
    private String middleName;
    
    @NotBlank(message = "Family name 1 is required")
    @Size(max = 100, message = "Family name 1 must not exceed 100 characters")
    private String familyName1;
    
    @Size(max = 100, message = "Family name 2 must not exceed 100 characters")
    private String familyName2;
    
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    
    @Size(max = 150, message = "Birth place must not exceed 150 characters")
    private String birthPlace;

    @FilterableId
    private UUID birthCountryId;

    @FilterableId
    private UUID nationalityCountryId;
    
    private Gender gender;
    private MaritalStatus maritalStatus;
    
    @Size(max = 50, message = "Tax ID number must not exceed 50 characters")
    private String taxIdNumber;
    
    private ResidencyStatus residencyStatus;
    
    @Size(max = 150, message = "Occupation must not exceed 150 characters")
    private String occupation;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly income must be greater than 0")
    private BigDecimal monthlyIncome;
    
    @Size(max = 20, message = "Suffix must not exceed 20 characters")
    private String suffix;

    @Min(value = 0, message = "Number of children must be non-negative")
    private Short numberOfChildren;

    @Size(max = 30, message = "employmentStatus must not exceed 30 characters")
    private String employmentStatus;

    @Size(max = 20, message = "employmentType must not exceed 20 characters")
    private String employmentType;

    @Size(max = 200, message = "employer must not exceed 200 characters")
    private String employer;

    private LocalDate employmentStartDate;

    @Min(value = 0, message = "annualPaydays must be non-negative")
    private Short annualPaydays;

    @Size(max = 20, message = "housingType must not exceed 20 characters")
    private String housingType;

    @DecimalMin(value = "0.0", inclusive = true, message = "housingCost must be non-negative")
    private BigDecimal housingCost;

    private LocalDate housingStartDate;

    @Min(value = 0, message = "existingLoans must be non-negative")
    private Short existingLoans;

    @DecimalMin(value = "0.0", inclusive = true, message = "otherDebts must be non-negative")
    private BigDecimal otherDebts;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}