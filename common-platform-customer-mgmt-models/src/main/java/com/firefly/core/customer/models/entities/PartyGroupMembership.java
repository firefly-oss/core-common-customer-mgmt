package com.firefly.core.customer.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Party Group Membership entity representing group memberships for parties.
 * Maps to the 'party_group_membership' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("party_group_membership")
public class PartyGroupMembership {

    @Id
    @Column("party_group_membership_id")
    private Long partyGroupMembershipId;

    @Column("group_id")
    private Long groupId;

    @Column("party_id")
    private Long partyId;

    @Column("is_active")
    private Boolean isActive;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}