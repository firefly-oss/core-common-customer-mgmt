package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.IdentityDocumentDTO;
import com.catalis.core.customer.models.entities.IdentityDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between IdentityDocument entity and IdentityDocumentDTO.
 */
@Mapper(componentModel = "spring")
public interface IdentityDocumentMapper {

    IdentityDocumentMapper INSTANCE = Mappers.getMapper(IdentityDocumentMapper.class);

    /**
     * Converts IdentityDocument entity to IdentityDocumentDTO.
     *
     * @param identityDocument the IdentityDocument entity to convert
     * @return the converted IdentityDocumentDTO
     */
    IdentityDocumentDTO toDTO(IdentityDocument identityDocument);

    /**
     * Converts IdentityDocumentDTO to IdentityDocument entity.
     *
     * @param identityDocumentDTO the IdentityDocumentDTO to convert
     * @return the converted IdentityDocument entity
     */
    IdentityDocument toEntity(IdentityDocumentDTO identityDocumentDTO);
}