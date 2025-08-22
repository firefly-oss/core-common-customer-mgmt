package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.PartyStatusDTO;
import com.catalis.core.customer.models.entities.PartyStatus;
import org.mapstruct.Mapper;
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
}