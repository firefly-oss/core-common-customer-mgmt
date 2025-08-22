package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.EmailContactDTO;
import com.catalis.core.customer.models.entities.EmailContact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between EmailContact entity and EmailContactDTO.
 */
@Mapper(componentModel = "spring")
public interface EmailContactMapper {

    EmailContactMapper INSTANCE = Mappers.getMapper(EmailContactMapper.class);

    /**
     * Converts EmailContact entity to EmailContactDTO.
     *
     * @param emailContact the EmailContact entity to convert
     * @return the converted EmailContactDTO
     */
    EmailContactDTO toDTO(EmailContact emailContact);

    /**
     * Converts EmailContactDTO to EmailContact entity.
     *
     * @param emailContactDTO the EmailContactDTO to convert
     * @return the converted EmailContact entity
     */
    EmailContact toEntity(EmailContactDTO emailContactDTO);
}