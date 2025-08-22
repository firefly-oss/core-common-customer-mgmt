package com.catalis.core.customer.interfaces.dtos;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Legal Entity entity representing organizational customers.
 * Used for transferring legal entity data between application layers.
 * This is a subtype of Party with a 1:1 relationship.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntityDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long legalEntityId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private Long partyId;

    @NotBlank(message = "Legal name is required")
    @Size(max = 200, message = "Legal name must not exceed 200 characters")
    private String legalName;
    
    @Size(max = 200, message = "Trade name must not exceed 200 characters")
    private String tradeName;
    
    @Size(max = 100, message = "Registration number must not exceed 100 characters")
    private String registrationNumber;
    
    @Size(max = 50, message = "Tax ID number must not exceed 50 characters")
    private String taxIdNumber;

    @FilterableId
    private Long legalFormId;

    @Past(message = "Incorporation date must be in the past")
    private LocalDate incorporationDate;
    
    @Size(max = 300, message = "Industry description must not exceed 300 characters")
    private String industryDescription;
    
    @Min(value = 0, message = "Headcount must be non-negative")
    private Long headcount;
    
    @DecimalMin(value = "0.0", inclusive = true, message = "Share capital must be non-negative")
    private BigDecimal shareCapital;
    
    @Pattern(regexp = "^https?://.*", message = "Website URL must be a valid HTTP/HTTPS URL")
    @Size(max = 500, message = "Website URL must not exceed 500 characters")
    private String websiteUrl;

    @FilterableId
    private Long incorporationCountryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}