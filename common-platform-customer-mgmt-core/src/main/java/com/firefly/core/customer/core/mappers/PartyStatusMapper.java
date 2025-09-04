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

import com.firefly.core.customer.interfaces.dtos.PartyStatusDTO;
import com.firefly.core.customer.interfaces.dtos.PhoneContactDTO;
import com.firefly.core.customer.models.entities.PartyStatus;
import com.firefly.core.customer.models.entities.PhoneContact;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between PartyStatus entity and PartyStatusDTO.
 */
@Mapper(componentModel = "spring")
public interface PartyStatusMapper {

    PartyStatusMapper INSTANCE = Mappers.getMapper(PartyStatusMapper.class);

    /**
     * Converts PartyStatus entity to PartyStatusDTO.
     *
     * @param partyStatus the PartyStatus entity to convert
     * @return the converted PartyStatusDTO
     */
    PartyStatusDTO toDTO(PartyStatus partyStatus);

    /**
     * Converts PartyStatusDTO to PartyStatus entity.
     *
     * @param partyStatusDTO the PartyStatusDTO to convert
     * @return the converted PartyStatus entity
     */
    PartyStatus toEntity(PartyStatusDTO partyStatusDTO);

    /**
     * Updates the specified PartyStatus entity with the non-null properties of the given PartyStatusDTO.
     * This method ignores the "createdAt" property during the mapping process.
     *
     * @param dto the PartyStatusDTO containing updated values
     * @param entity the PartyStatus entity to update
     */
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PartyStatusDTO dto, @MappingTarget PartyStatus entity);
}