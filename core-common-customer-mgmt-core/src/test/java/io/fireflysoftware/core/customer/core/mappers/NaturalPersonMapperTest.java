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

import io.fireflysoftware.core.customer.interfaces.dtos.NaturalPersonDTO;
import io.fireflysoftware.core.customer.models.entities.NaturalPerson;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class NaturalPersonMapperTest {

    private final NaturalPersonMapper mapper = NaturalPersonMapper.INSTANCE;

    @Test
    void toDTO_ShouldMapNumberOfChildren_WhenSet() {
        NaturalPerson entity = NaturalPerson.builder()
                .naturalPersonId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .givenName("Jane")
                .familyName("Doe")
                .numberOfChildren((short) 3)
                .build();

        NaturalPersonDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals((short) 3, dto.getNumberOfChildren());
    }

    @Test
    void toEntity_ShouldMapNumberOfChildren_WhenSet() {
        NaturalPersonDTO dto = NaturalPersonDTO.builder()
                .naturalPersonId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .givenName("John")
                .familyName("Smith")
                .numberOfChildren((short) 0)
                .build();

        NaturalPerson entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals((short) 0, entity.getNumberOfChildren());
    }

    @Test
    void toDTO_ShouldReturnNullNumberOfChildren_WhenEntityHasNullValue() {
        NaturalPerson entity = NaturalPerson.builder()
                .naturalPersonId(UUID.randomUUID())
                .partyId(UUID.randomUUID())
                .givenName("Anonymous")
                .familyName("Person")
                .build();

        NaturalPersonDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertNull(dto.getNumberOfChildren());
    }
}
