package com.firefly.core.customer.models.entities;

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
 * Politically Exposed Person entity representing PEP information for parties.
 * Maps to the 'politically_exposed_person' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("politically_exposed_person")
public class PoliticallyExposedPerson {

    @Id
    @Column("pep_id")
    private UUID pepId;

    @Column("party_id")
    private UUID partyId;

    @Column("pep")
    private Boolean pep;

    @Column("category")
    private String category;

    @Column("public_position")
    private String publicPosition;

    @Column("country_of_position_id")
    private UUID countryOfPositionId;

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