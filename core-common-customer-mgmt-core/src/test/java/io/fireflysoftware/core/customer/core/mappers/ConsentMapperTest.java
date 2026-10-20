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

package io.fireflysoftware.core.customer.core.mappers;

import io.fireflysoftware.core.customer.interfaces.dtos.ConsentDTO;
import io.fireflysoftware.core.customer.models.entities.Consent;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConsentMapperTest {

    private final ConsentMapper mapper = ConsentMapper.INSTANCE;

    @Test
    void toDTO_ShouldMapApplicationId_WhenSet() {
        UUID applicationId = UUID.randomUUID();
        Consent entity = Consent.builder()
                .consentId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .consentTypeId(UUID.randomUUID())
                .granted(Boolean.TRUE)
                .applicationId(applicationId)
                .build();

        ConsentDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(applicationId, dto.getApplicationId());
    }

    @Test
    void toEntity_ShouldMapApplicationId_WhenSet() {
        UUID applicationId = UUID.randomUUID();
        ConsentDTO dto = ConsentDTO.builder()
                .consentId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .consentTypeId(UUID.randomUUID())
                .granted(Boolean.TRUE)
                .applicationId(applicationId)
                .build();

        Consent entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(applicationId, entity.getApplicationId());
    }

    @Test
    void toDTO_ShouldReturnNullApplicationId_WhenEntityHasNullValue() {
        Consent entity = Consent.builder()
                .consentId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .consentTypeId(UUID.randomUUID())
                .granted(Boolean.FALSE)
                .build();

        ConsentDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertNull(dto.getApplicationId());
    }
}
