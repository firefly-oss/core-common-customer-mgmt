package com.firefly.core.customer.core.mappers;

import com.firefly.core.customer.interfaces.dtos.PartyProviderDTO;
import com.firefly.core.customer.models.entities.PartyProvider;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between PartyProvider entity and PartyProviderDTO.
 */
@Mapper(componentModel = "spring")
public interface PartyProviderMapper {

    PartyProviderMapper INSTANCE = Mappers.getMapper(PartyProviderMapper.class);

    /**
     * Converts PartyProvider entity to PartyProviderDTO.
     *
     * @param partyProvider the PartyProvider entity to convert
     * @return the converted PartyProviderDTO
     */
    PartyProviderDTO toDTO(PartyProvider partyProvider);

    /**
     * Converts PartyProviderDTO to PartyProvider entity.
     *
     * @param partyProviderDTO the PartyProviderDTO to convert
     * @return the converted PartyProvider entity
     */
    PartyProvider toEntity(PartyProviderDTO partyProviderDTO);
}