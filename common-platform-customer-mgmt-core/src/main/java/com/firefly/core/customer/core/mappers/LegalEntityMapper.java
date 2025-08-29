package com.firefly.core.customer.core.mappers;

import com.firefly.core.customer.interfaces.dtos.LegalEntityDTO;
import com.firefly.core.customer.interfaces.dtos.NaturalPersonDTO;
import com.firefly.core.customer.models.entities.LegalEntity;
import com.firefly.core.customer.models.entities.NaturalPerson;
import org.mapstruct.*;
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

    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(LegalEntityDTO dto, @MappingTarget LegalEntity entity);
}