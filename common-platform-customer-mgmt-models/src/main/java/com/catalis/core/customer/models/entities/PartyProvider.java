package com.catalis.core.customer.models.entities;

import com.catalis.core.customer.interfaces.enums.ProviderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Party Provider entity representing external system mappings for parties.
 * Maps to the 'party_provider' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("party_provider")
public class PartyProvider {

    @Id
    @Column("party_provider_id")
    private Long partyProviderId;

    @Column("party_id")
    private Long partyId;

    @Column("provider_name")
    private String providerName;

    @Column("external_reference")
    private String externalReference;

    @Column("provider_status")
    private ProviderStatus providerStatus;

    @Column("last_sync_at")
    private LocalDateTime lastSyncAt;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}