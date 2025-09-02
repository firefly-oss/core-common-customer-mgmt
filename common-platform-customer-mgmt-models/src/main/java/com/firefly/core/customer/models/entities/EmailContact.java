package com.firefly.core.customer.models.entities;

import com.firefly.core.customer.interfaces.enums.EmailKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Email Contact entity representing email contact information for parties.
 * Maps to the 'email_contact' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("email_contact")
public class EmailContact {

    @Id
    @Column("email_contact_id")
    private UUID emailContactId;

    @Column("party_id")
    private UUID partyId;

    @Column("email")
    private String email;

    @Column("email_kind")
    private EmailKind emailKind;

    @Column("is_primary")
    private Boolean isPrimary;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}