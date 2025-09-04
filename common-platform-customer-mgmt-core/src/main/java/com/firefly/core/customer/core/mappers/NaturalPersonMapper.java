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

import com.firefly.core.customer.interfaces.dtos.NaturalPersonDTO;
import com.firefly.core.customer.models.entities.NaturalPerson;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between NaturalPerson entity and NaturalPersonDTO.
 */
@Mapper(componentModel = "spring")
public interface NaturalPersonMapper {

    NaturalPersonMapper INSTANCE = Mappers.getMapper(NaturalPersonMapper.class);

    /**
     * Converts NaturalPerson entity to NaturalPersonDTO.
     *
     * @param naturalPerson the NaturalPerson entity to convert
     * @return the converted NaturalPersonDTO
     */
    NaturalPersonDTO toDTO(NaturalPerson naturalPerson);

    /**
     * Converts NaturalPersonDTO to NaturalPerson entity.
     *
     * @param naturalPersonDTO the NaturalPersonDTO to convert
     * @return the converted NaturalPerson entity
     */
    NaturalPerson toEntity(NaturalPersonDTO naturalPersonDTO);

    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(NaturalPersonDTO dto, @MappingTarget NaturalPerson entity);

}