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

import com.firefly.core.customer.interfaces.dtos.EmailContactDTO;
import com.firefly.core.customer.interfaces.dtos.PhoneContactDTO;
import com.firefly.core.customer.models.entities.EmailContact;
import com.firefly.core.customer.models.entities.PhoneContact;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between PhoneContact entity and PhoneContactDTO.
 */
@Mapper(componentModel = "spring")
public interface PhoneContactMapper {

    PhoneContactMapper INSTANCE = Mappers.getMapper(PhoneContactMapper.class);

    /**
     * Converts PhoneContact entity to PhoneContactDTO.
     *
     * @param phoneContact the PhoneContact entity to convert
     * @return the converted PhoneContactDTO
     */
    PhoneContactDTO toDTO(PhoneContact phoneContact);

    /**
     * Converts PhoneContactDTO to PhoneContact entity.
     *
     * @param phoneContactDTO the PhoneContactDTO to convert
     * @return the converted PhoneContact entity
     */
    PhoneContact toEntity(PhoneContactDTO phoneContactDTO);

    /**
     * Updates the properties of an existing PhoneContact entity using data from a PhoneContactDTO.
     * Fields with null values in the DTO are ignored during mapping, and the 'createdAt' field
     * of the entity is not updated.
     *
     * @param dto the PhoneContactDTO containing the data to update the entity
     * @param entity the PhoneContact entity to update
     */
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PhoneContactDTO dto, @MappingTarget PhoneContact entity);

}