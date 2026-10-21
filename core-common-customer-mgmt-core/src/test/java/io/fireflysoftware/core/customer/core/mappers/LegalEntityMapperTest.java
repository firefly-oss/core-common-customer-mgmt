/*
 * Copyright 2025 Firefly Software Foundation
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

package io.fireflysoftware.core.customer.core.mappers;

import io.fireflysoftware.core.customer.interfaces.dtos.LegalEntityDTO;
import io.fireflysoftware.core.customer.models.entities.LegalEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LegalEntityMapperTest {

    private final LegalEntityMapper mapper = LegalEntityMapper.INSTANCE;

    @Test
    void toDTO_ShouldMapAllNewCompanyDataFields() {
        LegalEntity entity = LegalEntity.builder()
                .legalEntityId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .legalName("Acme Corp")
                .employeeRange("26-50")
                .annualRevenue(new BigDecimal("1234567.89"))
                .contactName("Jane Director")
                .contactPosition("CFO")
                .contactEmail("jane@acme.example")
                .contactPhone("+34 600 000 000")
                .build();

        LegalEntityDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals("26-50", dto.getEmployeeRange());
        assertEquals(new BigDecimal("1234567.89"), dto.getAnnualRevenue());
        assertEquals("Jane Director", dto.getContactName());
        assertEquals("CFO", dto.getContactPosition());
        assertEquals("jane@acme.example", dto.getContactEmail());
        assertEquals("+34 600 000 000", dto.getContactPhone());
    }

    @Test
    void toEntity_ShouldMapAllNewCompanyDataFields() {
        LegalEntityDTO dto = LegalEntityDTO.builder()
                .legalEntityId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .legalName("Beta Ltd")
                .employeeRange("250+")
                .annualRevenue(new BigDecimal("9999999.99"))
                .contactName("John Buyer")
                .contactPosition("Procurement Manager")
                .contactEmail("john@beta.example")
                .contactPhone("+34 911 222 333")
                .build();

        LegalEntity entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("250+", entity.getEmployeeRange());
        assertEquals(new BigDecimal("9999999.99"), entity.getAnnualRevenue());
        assertEquals("John Buyer", entity.getContactName());
        assertEquals("Procurement Manager", entity.getContactPosition());
        assertEquals("john@beta.example", entity.getContactEmail());
        assertEquals("+34 911 222 333", entity.getContactPhone());
    }

    @Test
    void updateEntityFromDto_ShouldOverwriteNewCompanyDataFields() {
        LegalEntity entity = LegalEntity.builder()
                .legalEntityId(UUID.randomUUID())
                .legalName("Existing")
                .employeeRange("1-5")
                .build();

        LegalEntityDTO dto = LegalEntityDTO.builder()
                .legalName("Existing")
                .partyId(UUID.randomUUID())
                .employeeRange("51-250")
                .annualRevenue(new BigDecimal("500000.00"))
                .contactName("Updated Name")
                .contactPosition("CEO")
                .contactEmail("ceo@existing.example")
                .contactPhone("+34 600 111 222")
                .build();

        mapper.updateEntityFromDto(dto, entity);

        assertEquals("51-250", entity.getEmployeeRange());
        assertEquals(new BigDecimal("500000.00"), entity.getAnnualRevenue());
        assertEquals("Updated Name", entity.getContactName());
        assertEquals("CEO", entity.getContactPosition());
        assertEquals("ceo@existing.example", entity.getContactEmail());
        assertEquals("+34 600 111 222", entity.getContactPhone());
    }
}
