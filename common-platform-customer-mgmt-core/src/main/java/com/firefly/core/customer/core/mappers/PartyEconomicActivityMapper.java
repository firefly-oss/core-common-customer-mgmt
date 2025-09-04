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

import com.firefly.core.customer.interfaces.dtos.PartyEconomicActivityDTO;
import com.firefly.core.customer.models.entities.PartyEconomicActivity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between PartyEconomicActivity entity and PartyEconomicActivityDTO.
 */
@Mapper(componentModel = "spring")
public interface PartyEconomicActivityMapper {

    PartyEconomicActivityMapper INSTANCE = Mappers.getMapper(PartyEconomicActivityMapper.class);

    /**
     * Converts PartyEconomicActivity entity to PartyEconomicActivityDTO.
     *
     * @param partyEconomicActivity the PartyEconomicActivity entity to convert
     * @return the converted PartyEconomicActivityDTO
     */
    PartyEconomicActivityDTO toDTO(PartyEconomicActivity partyEconomicActivity);

    /**
     * Converts PartyEconomicActivityDTO to PartyEconomicActivity entity.
     *
     * @param partyEconomicActivityDTO the PartyEconomicActivityDTO to convert
     * @return the converted PartyEconomicActivity entity
     */
    PartyEconomicActivity toEntity(PartyEconomicActivityDTO partyEconomicActivityDTO);
}