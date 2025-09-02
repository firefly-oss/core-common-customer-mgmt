package com.firefly.core.customer.interfaces.dtos;

import com.firefly.core.customer.interfaces.enums.AddressKind;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Address entity representing addresses for parties.
 * Used for transferring address data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID addressId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotNull(message = "Address kind is required")
    private AddressKind addressKind;
    
    @NotBlank(message = "Address line 1 is required")
    @Size(max = 255, message = "Address line 1 must not exceed 255 characters")
    private String line1;
    
    @Size(max = 255, message = "Address line 2 must not exceed 255 characters")
    private String line2;
    
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;
    
    @Size(max = 100, message = "Region must not exceed 100 characters")
    private String region;
    
    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    @FilterableId
    @NotNull(message = "Country ID is required")
    private UUID countryId;

    private Boolean isPrimary;
    
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private Double latitude;
    
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double longitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}