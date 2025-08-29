package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.EmailContactDTO;
import com.catalis.core.customer.interfaces.dtos.LegalEntityDTO;
import com.catalis.core.customer.models.entities.EmailContact;
import com.catalis.core.customer.models.entities.LegalEntity;
import org.mapstruct.*;
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

    /**
     * Updates the specified EmailContact entity with the non-null properties of the given EmailContactDTO.
     * This method ignores the "createdAt" property during the mapping process.
     *
     * @param dto the EmailContactDTO containing updated values
     * @param entity the EmailContact entity to update
     */
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EmailContactDTO dto, @MappingTarget EmailContact entity);

}