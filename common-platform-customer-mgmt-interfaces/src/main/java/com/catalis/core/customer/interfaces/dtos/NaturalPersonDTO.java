package com.catalis.core.customer.interfaces.dtos;

import com.catalis.core.customer.interfaces.enums.Gender;
import com.catalis.core.customer.interfaces.enums.MaritalStatus;
import com.catalis.core.customer.interfaces.enums.ResidencyStatus;
import com.catalis.core.utils.annotations.FilterableId;
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
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Long naturalPersonId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private Long partyId;

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
    private Long birthCountryId;

    @FilterableId
    private Long nationalityCountryId;
    
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}