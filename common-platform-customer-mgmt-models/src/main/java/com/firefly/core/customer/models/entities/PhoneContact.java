package com.firefly.core.customer.models.entities;

import com.firefly.core.customer.interfaces.enums.PhoneKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Phone Contact entity representing phone contact information for parties.
 * Maps to the 'phone_contact' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("phone_contact")
public class PhoneContact {

    @Id
    @Column("phone_contact_id")
    private Long phoneContactId;

    @Column("party_id")
    private Long partyId;

    @Column("phone_number")
    private String phoneNumber;

    @Column("phone_kind")
    private PhoneKind phoneKind;

    @Column("is_primary")
    private Boolean isPrimary;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("extension")
    private String extension;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}