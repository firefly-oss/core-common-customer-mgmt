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