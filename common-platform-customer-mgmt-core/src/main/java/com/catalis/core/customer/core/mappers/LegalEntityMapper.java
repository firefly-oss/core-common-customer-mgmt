package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.LegalEntityDTO;
import com.catalis.core.customer.models.entities.LegalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between LegalEntity entity and LegalEntityDTO.
 */
@Mapper(componentModel = "spring")
public interface LegalEntityMapper {

    LegalEntityMapper INSTANCE = Mappers.getMapper(LegalEntityMapper.class);

    /**
     * Converts LegalEntity entity to LegalEntityDTO.
     *
     * @param legalEntity the LegalEntity entity to convert
     * @return the converted LegalEntityDTO
     */
    LegalEntityDTO toDTO(LegalEntity legalEntity);

    /**
     * Converts LegalEntityDTO to LegalEntity entity.
     *
     * @param legalEntityDTO the LegalEntityDTO to convert
     * @return the converted LegalEntity entity
     */
    LegalEntity toEntity(LegalEntityDTO legalEntityDTO);
}