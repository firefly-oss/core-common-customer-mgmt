package com.firefly.core.customer.models.entities;

import com.firefly.core.customer.interfaces.enums.PartyKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Party entity representing the supertype for all customer types.
 * Maps to the 'party' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("party")
public class Party {

    @Id
    @Column("party_id")
    private Long partyId;

    @Column("party_kind")
    private PartyKind partyKind;

    @Column("preferred_language")
    private String preferredLanguage;

    @Column("source_system")
    private String sourceSystem;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}