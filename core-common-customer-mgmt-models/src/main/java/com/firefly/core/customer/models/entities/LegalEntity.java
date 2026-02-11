/*
 * Copyright 2025 Firefly Software Solutions Inc
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


package com.firefly.core.customer.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Legal Entity entity representing organizational customers.
 * Maps to the 'legal_entity' table in PostgreSQL.
 * This is a subtype of Party with a 1:1 relationship.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("legal_entity")
public class LegalEntity {

    @Id
    @Column("legal_entity_id")
    private UUID legalEntityId;

    @Column("party_id")
    private UUID partyId;

    @Column("legal_name")
    private String legalName;

    @Column("trade_name")
    private String tradeName;

    @Column("registration_number")
    private String registrationNumber;

    @Column("tax_id_number")
    private String taxIdNumber;

    @Column("legal_form_id")
    private UUID legalFormId;

    @Column("incorporation_date")
    private LocalDate incorporationDate;

    @Column("industry_description")
    private String industryDescription;

    @Column("headcount")
    private Long headcount;

    @Column("share_capital")
    private BigDecimal shareCapital;

    @Column("website_url")
    private String websiteUrl;

    @Column("incorporation_country_id")
    private UUID incorporationCountryId;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}