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

import com.firefly.core.customer.interfaces.enums.AddressKind;
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
 * Address entity representing addresses for parties.
 * Maps to the 'address' table in PostgreSQL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("address")
public class Address {

    @Id
    @Column("address_id")
    private UUID addressId;

    @Column("party_id")
    private UUID partyId;

    @Column("address_kind")
    private AddressKind addressKind;

    @Column("line1")
    private String line1;

    @Column("line2")
    private String line2;

    @Column("city")
    private String city;

    @Column("region")
    private String region;

    @Column("postal_code")
    private String postalCode;

    @Column("country_id")
    private UUID countryId;

    @Column("is_primary")
    private Boolean isPrimary;

    @Column("latitude")
    private Double latitude;

    @Column("longitude")
    private Double longitude;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}