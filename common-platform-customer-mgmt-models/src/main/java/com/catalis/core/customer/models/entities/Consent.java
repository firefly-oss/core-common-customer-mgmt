package com.catalis.core.customer.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Consent entity representing user consent records for parties.
 * Maps to the 'consent' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("consent")
public class Consent {

    @Id
    @Column("consent_id")
    private Long consentId;

    @Column("party_id")
    private Long partyId;

    @Column("consent_type_id")
    private Long consentTypeId;

    @Column("granted")
    private Boolean granted;

    @Column("granted_at")
    private LocalDateTime grantedAt;

    @Column("revoked_at")
    private LocalDateTime revokedAt;

    @Column("channel")
    private String channel;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}