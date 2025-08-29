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