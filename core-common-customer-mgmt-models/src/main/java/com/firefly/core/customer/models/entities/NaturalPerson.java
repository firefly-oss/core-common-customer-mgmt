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

import com.firefly.core.customer.interfaces.enums.Gender;
import com.firefly.core.customer.interfaces.enums.MaritalStatus;
import com.firefly.core.customer.interfaces.enums.ResidencyStatus;
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
 * Natural Person entity representing individual customers.
 * Maps to the 'natural_person' table in PostgreSQL.
 * This is a subtype of Party with a 1:1 relationship.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("natural_person")
public class NaturalPerson {

    @Id
    @Column("natural_person_id")
    private UUID naturalPersonId;

    @Column("party_id")
    private UUID partyId;

    @Column("title")
    private String title;

    @Column("given_name")
    private String givenName;

    @Column("middle_name")
    private String middleName;

    @Column("family_name1")
    private String familyName1;

    @Column("family_name2")
    private String familyName2;

    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    @Column("birth_place")
    private String birthPlace;

    @Column("birth_country_id")
    private UUID birthCountryId;

    @Column("nationality_country_id")
    private UUID nationalityCountryId;

    @Column("gender")
    private Gender gender;

    @Column("marital_status")
    private MaritalStatus maritalStatus;

    @Column("tax_id_number")
    private String taxIdNumber;

    @Column("residency_status")
    private ResidencyStatus residencyStatus;

    @Column("occupation")
    private String occupation;

    @Column("monthly_income")
    private BigDecimal monthlyIncome;

    @Column("suffix")
    private String suffix;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}