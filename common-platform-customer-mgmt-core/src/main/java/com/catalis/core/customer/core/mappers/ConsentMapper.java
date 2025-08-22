package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.ConsentDTO;
import com.catalis.core.customer.models.entities.Consent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between Consent entity and ConsentDTO.
 */
@Mapper(componentModel = "spring")
public interface ConsentMapper {

    ConsentMapper INSTANCE = Mappers.getMapper(ConsentMapper.class);

    /**
     * Converts Consent entity to ConsentDTO.
     *
     * @param consent the Consent entity to convert
     * @return the converted ConsentDTO
     */
    ConsentDTO toDTO(Consent consent);

    /**
     * Converts ConsentDTO to Consent entity.
     *
     * @param consentDTO the ConsentDTO to convert
     * @return the converted Consent entity
     */
    Consent toEntity(ConsentDTO consentDTO);
}