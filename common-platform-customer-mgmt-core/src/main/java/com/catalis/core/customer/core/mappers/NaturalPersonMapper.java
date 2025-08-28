package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.NaturalPersonDTO;
import com.catalis.core.customer.models.entities.NaturalPerson;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between NaturalPerson entity and NaturalPersonDTO.
 */
@Mapper(componentModel = "spring")
public interface NaturalPersonMapper {

    NaturalPersonMapper INSTANCE = Mappers.getMapper(NaturalPersonMapper.class);

    /**
     * Converts NaturalPerson entity to NaturalPersonDTO.
     *
     * @param naturalPerson the NaturalPerson entity to convert
     * @return the converted NaturalPersonDTO
     */
    NaturalPersonDTO toDTO(NaturalPerson naturalPerson);

    /**
     * Converts NaturalPersonDTO to NaturalPerson entity.
     *
     * @param naturalPersonDTO the NaturalPersonDTO to convert
     * @return the converted NaturalPerson entity
     */
    NaturalPerson toEntity(NaturalPersonDTO naturalPersonDTO);

    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(NaturalPersonDTO dto, @MappingTarget NaturalPerson entity);

}