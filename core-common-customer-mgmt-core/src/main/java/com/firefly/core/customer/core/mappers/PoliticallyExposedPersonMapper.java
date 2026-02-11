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


package com.firefly.core.customer.core.mappers;

import com.firefly.core.customer.interfaces.dtos.PoliticallyExposedPersonDTO;
import com.firefly.core.customer.models.entities.PoliticallyExposedPerson;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between PoliticallyExposedPerson entity and PoliticallyExposedPersonDTO.
 */
@Mapper(componentModel = "spring")
public interface PoliticallyExposedPersonMapper {

    PoliticallyExposedPersonMapper INSTANCE = Mappers.getMapper(PoliticallyExposedPersonMapper.class);

    /**
     * Converts PoliticallyExposedPerson entity to PoliticallyExposedPersonDTO.
     *
     * @param politicallyExposedPerson the PoliticallyExposedPerson entity to convert
     * @return the converted PoliticallyExposedPersonDTO
     */
    PoliticallyExposedPersonDTO toDTO(PoliticallyExposedPerson politicallyExposedPerson);

    /**
     * Converts PoliticallyExposedPersonDTO to PoliticallyExposedPerson entity.
     *
     * @param politicallyExposedPersonDTO the PoliticallyExposedPersonDTO to convert
     * @return the converted PoliticallyExposedPerson entity
     */
    PoliticallyExposedPerson toEntity(PoliticallyExposedPersonDTO politicallyExposedPersonDTO);
}