/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.customer.core.mappers;

import com.firefly.core.customer.interfaces.dtos.IdentityDocumentDTO;
import com.firefly.core.customer.models.entities.IdentityDocument;
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