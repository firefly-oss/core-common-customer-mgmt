package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.PhoneContactDTO;
import com.catalis.core.customer.models.entities.PhoneContact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between PhoneContact entity and PhoneContactDTO.
 */
@Mapper(componentModel = "spring")
public interface PhoneContactMapper {

    PhoneContactMapper INSTANCE = Mappers.getMapper(PhoneContactMapper.class);

    /**
     * Converts PhoneContact entity to PhoneContactDTO.
     *
     * @param phoneContact the PhoneContact entity to convert
     * @return the converted PhoneContactDTO
     */
    PhoneContactDTO toDTO(PhoneContact phoneContact);

    /**
     * Converts PhoneContactDTO to PhoneContact entity.
     *
     * @param phoneContactDTO the PhoneContactDTO to convert
     * @return the converted PhoneContact entity
     */
    PhoneContact toEntity(PhoneContactDTO phoneContactDTO);
}