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
import com.firefly.core.customer.interfaces.dtos.LegalEntityDTO;
import com.firefly.core.customer.models.entities.EmailContact;
import com.firefly.core.customer.models.entities.LegalEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between EmailContact entity and EmailContactDTO.
 */
@Mapper(componentModel = "spring")
public interface EmailContactMapper {

    EmailContactMapper INSTANCE = Mappers.getMapper(EmailContactMapper.class);

    /**
     * Converts EmailContact entity to EmailContactDTO.
     *
     * @param emailContact the EmailContact entity to convert
     * @return the converted EmailContactDTO
     */
    EmailContactDTO toDTO(EmailContact emailContact);

    /**
     * Converts EmailContactDTO to EmailContact entity.
     *
     * @param emailContactDTO the EmailContactDTO to convert
     * @return the converted EmailContact entity
     */
    EmailContact toEntity(EmailContactDTO emailContactDTO);

    /**
     * Updates the specified EmailContact entity with the non-null properties of the given EmailContactDTO.
     * This method ignores the "createdAt" property during the mapping process.
     *
     * @param dto the EmailContactDTO containing updated values
     * @param entity the EmailContact entity to update
     */
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EmailContactDTO dto, @MappingTarget EmailContact entity);

}