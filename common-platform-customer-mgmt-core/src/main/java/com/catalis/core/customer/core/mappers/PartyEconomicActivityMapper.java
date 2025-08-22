package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.PartyEconomicActivityDTO;
import com.catalis.core.customer.models.entities.PartyEconomicActivity;
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