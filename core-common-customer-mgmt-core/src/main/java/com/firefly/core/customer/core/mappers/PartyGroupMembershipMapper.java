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

import com.firefly.core.customer.interfaces.dtos.PartyGroupMembershipDTO;
import com.firefly.core.customer.models.entities.PartyGroupMembership;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between PartyGroupMembership entity and PartyGroupMembershipDTO.
 */
@Mapper(componentModel = "spring")
public interface PartyGroupMembershipMapper {

    PartyGroupMembershipMapper INSTANCE = Mappers.getMapper(PartyGroupMembershipMapper.class);

    /**
     * Converts PartyGroupMembership entity to PartyGroupMembershipDTO.
     *
     * @param partyGroupMembership the PartyGroupMembership entity to convert
     * @return the converted PartyGroupMembershipDTO
     */
    PartyGroupMembershipDTO toDTO(PartyGroupMembership partyGroupMembership);

    /**
     * Converts PartyGroupMembershipDTO to PartyGroupMembership entity.
     *
     * @param partyGroupMembershipDTO the PartyGroupMembershipDTO to convert
     * @return the converted PartyGroupMembership entity
     */
    PartyGroupMembership toEntity(PartyGroupMembershipDTO partyGroupMembershipDTO);
}