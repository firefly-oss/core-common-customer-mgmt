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
 * Party Relationship entity representing relationships between parties.
 * Maps to the 'party_relationship' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("party_relationship")
public class PartyRelationship {

    @Id
    @Column("party_relationship_id")
    private Long partyRelationshipId;

    @Column("from_party_id")
    private Long fromPartyId;

    @Column("to_party_id")
    private Long toPartyId;

    @Column("relationship_type_id")
    private Long relationshipTypeId;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("active")
    private Boolean active;

    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}