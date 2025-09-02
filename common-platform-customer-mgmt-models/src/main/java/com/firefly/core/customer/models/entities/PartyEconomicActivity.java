package com.firefly.core.customer.models.entities;

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
 * Party Economic Activity entity representing economic activities for parties.
 * Maps to the 'party_economic_activity' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("party_economic_activity")
public class PartyEconomicActivity {

    @Id
    @Column("party_economic_activity_id")
    private UUID partyEconomicActivityId;

    @Column("party_id")
    private UUID partyId;

    @Column("economic_activity_id")
    private UUID economicActivityId;

    @Column("annual_turnover")
    private BigDecimal annualTurnover;

    @Column("currency_code")
    private String currencyCode;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("is_primary")
    private Boolean isPrimary;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}