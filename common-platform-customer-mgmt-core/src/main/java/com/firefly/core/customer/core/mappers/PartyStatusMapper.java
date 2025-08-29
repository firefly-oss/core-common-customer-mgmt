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