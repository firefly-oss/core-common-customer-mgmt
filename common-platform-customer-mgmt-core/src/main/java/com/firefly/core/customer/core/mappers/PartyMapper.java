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

import com.firefly.core.customer.interfaces.dtos.PartyDTO;
import com.firefly.core.customer.models.entities.Party;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between Party entity and PartyDTO.
 */
@Mapper(componentModel = "spring")
public interface PartyMapper {

    PartyMapper INSTANCE = Mappers.getMapper(PartyMapper.class);

    /**
     * Converts Party entity to PartyDTO.
     *
     * @param party the Party entity to convert
     * @return the converted PartyDTO
     */
    PartyDTO toDTO(Party party);

    /**
     * Converts PartyDTO to Party entity.
     *
     * @param partyDTO the PartyDTO to convert
     * @return the converted Party entity
     */
    Party toEntity(PartyDTO partyDTO);
}