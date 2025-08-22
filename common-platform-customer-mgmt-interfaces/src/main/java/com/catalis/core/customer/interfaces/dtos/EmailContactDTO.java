package com.catalis.core.customer.interfaces.dtos;

import com.catalis.core.customer.interfaces.enums.EmailKind;
import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Email Contact entity representing email contact information for parties.
 * Used for transferring email contact data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailContactDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long emailContactId;

    @FilterableId
    @NotNull(message = "Party ID is required")
    private Long partyId;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 320, message = "Email must not exceed 320 characters")
    private String email;
    
    @NotNull(message = "Email kind is required")
    private EmailKind emailKind;
    
    private Boolean isPrimary;
    private Boolean isVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}