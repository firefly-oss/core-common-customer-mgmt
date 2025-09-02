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
 * Identity Document entity representing identity documents for parties.
 * Maps to the 'identity_document' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("identity_document")
public class IdentityDocument {

    @Id
    @Column("identity_document_id")
    private UUID identityDocumentId;

    @Column("party_id")
    private UUID partyId;

    @Column("identity_document_category_id")
    private UUID identityDocumentCategoryId;

    @Column("identity_document_type_id")
    private UUID identityDocumentTypeId;

    @Column("document_number")
    private String documentNumber;

    @Column("issuing_country_id")
    private UUID issuingCountryId;

    @Column("issue_date")
    private LocalDateTime issueDate;

    @Column("expiry_date")
    private LocalDateTime expiryDate;

    @Column("issuing_authority")
    private String issuingAuthority;

    @Column("validated")
    private Boolean validated;

    @Column("document_uri")
    private String documentUri;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}