package com.firefly.core.customer.core.mappers;

import com.firefly.core.customer.interfaces.dtos.PartyRelationshipDTO;
import com.firefly.core.customer.models.entities.PartyRelationship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between PartyRelationship entity and PartyRelationshipDTO.
 */
@Mapper(componentModel = "spring")
public interface PartyRelationshipMapper {

    PartyRelationshipMapper INSTANCE = Mappers.getMapper(PartyRelationshipMapper.class);

    /**
     * Converts PartyRelationship entity to PartyRelationshipDTO.
     *
     * @param partyRelationship the PartyRelationship entity to convert
     * @return the converted PartyRelationshipDTO
     */
    PartyRelationshipDTO toDTO(PartyRelationship partyRelationship);

    /**
     * Converts PartyRelationshipDTO to PartyRelationship entity.
     *
     * @param partyRelationshipDTO the PartyRelationshipDTO to convert
     * @return the converted PartyRelationship entity
     */
    PartyRelationship toEntity(PartyRelationshipDTO partyRelationshipDTO);
}