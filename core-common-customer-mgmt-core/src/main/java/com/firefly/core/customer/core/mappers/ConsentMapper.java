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

import com.firefly.core.customer.interfaces.dtos.ConsentDTO;
import com.firefly.core.customer.models.entities.Consent;
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