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
    private Long naturalPersonId;

    @Column("party_id")
    private Long partyId;

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
    private Long birthCountryId;

    @Column("nationality_country_id")
    private Long nationalityCountryId;

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