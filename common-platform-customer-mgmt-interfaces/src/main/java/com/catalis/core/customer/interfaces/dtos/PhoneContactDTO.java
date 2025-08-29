package com.catalis.core.customer.interfaces.dtos;

import com.catalis.core.customer.interfaces.enums.PhoneKind;
import com.catalis.core.utils.annotations.FilterableId;
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
    private Long phoneContactId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private Long partyId;
    
    @NotBlank(groups = OnCreate.class, message = "Phone number is required")
    @Pattern(regexp = "^[\\+]?[1-9]\\d{1,14}$", message = "Phone number must be a valid format")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;
    
    @NotNull(groups = OnCreate.class, message = "Phone kind is required")
    private PhoneKind phoneKind;
    
    private Boolean isPrimary;
    private Boolean isVerified;
    
    @Size(max = 10, message = "Extension must not exceed 10 characters")
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}