package com.firefly.core.customer.core.mappers;

import com.firefly.core.customer.interfaces.dtos.PoliticallyExposedPersonDTO;
import com.firefly.core.customer.models.entities.PoliticallyExposedPerson;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between PoliticallyExposedPerson entity and PoliticallyExposedPersonDTO.
 */
@Mapper(componentModel = "spring")
public interface PoliticallyExposedPersonMapper {

    PoliticallyExposedPersonMapper INSTANCE = Mappers.getMapper(PoliticallyExposedPersonMapper.class);

    /**
     * Converts PoliticallyExposedPerson entity to PoliticallyExposedPersonDTO.
     *
     * @param politicallyExposedPerson the PoliticallyExposedPerson entity to convert
     * @return the converted PoliticallyExposedPersonDTO
     */
    PoliticallyExposedPersonDTO toDTO(PoliticallyExposedPerson politicallyExposedPerson);

    /**
     * Converts PoliticallyExposedPersonDTO to PoliticallyExposedPerson entity.
     *
     * @param politicallyExposedPersonDTO the PoliticallyExposedPersonDTO to convert
     * @return the converted PoliticallyExposedPerson entity
     */
    PoliticallyExposedPerson toEntity(PoliticallyExposedPersonDTO politicallyExposedPersonDTO);
}