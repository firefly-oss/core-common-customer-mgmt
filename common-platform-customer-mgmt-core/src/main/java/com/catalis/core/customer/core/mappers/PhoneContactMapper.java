package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.EmailContactDTO;
import com.catalis.core.customer.interfaces.dtos.PhoneContactDTO;
import com.catalis.core.customer.models.entities.EmailContact;
import com.catalis.core.customer.models.entities.PhoneContact;
import org.mapstruct.*;
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

    /**
     * Updates the properties of an existing PhoneContact entity using data from a PhoneContactDTO.
     * Fields with null values in the DTO are ignored during mapping, and the 'createdAt' field
     * of the entity is not updated.
     *
     * @param dto the PhoneContactDTO containing the data to update the entity
     * @param entity the PhoneContact entity to update
     */
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PhoneContactDTO dto, @MappingTarget PhoneContact entity);

}