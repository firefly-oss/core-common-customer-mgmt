package com.firefly.core.customer.models.entities;

import com.firefly.core.customer.interfaces.enums.StatusCode;
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
 * Party Status entity representing status history for parties.
 * Maps to the 'party_status' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("party_status")
public class PartyStatus {

    @Id
    @Column("party_status_id")
    private UUID partyStatusId;

    @Column("party_id")
    private UUID partyId;

    @Column("status_code")
    private StatusCode statusCode;

    @Column("status_reason")
    private String statusReason;

    @Column("valid_from")
    private LocalDateTime validFrom;

    @Column("valid_to")
    private LocalDateTime validTo;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}