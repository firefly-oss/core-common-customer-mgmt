/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.customer.models.entities;

import io.fireflysoftware.core.customer.interfaces.enums.RelationshipKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private UUID partyRelationshipId;

    @Column("source_party_id")
    private UUID sourcePartyId;

    @Column("target_party_id")
    private UUID targetPartyId;

    @Column("relationship_kind")
    private RelationshipKind relationshipKind;

    @Column("relationship_type_id")
    private UUID relationshipTypeId;

    @Column("ownership_percentage")
    private BigDecimal ownershipPercentage;

    @Column("tenant_id")
    private UUID tenantId;

    @Column("effective_from")
    private LocalDateTime effectiveFrom;

    @Column("effective_to")
    private LocalDateTime effectiveTo;

    @Column("active")
    private Boolean active;

    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}