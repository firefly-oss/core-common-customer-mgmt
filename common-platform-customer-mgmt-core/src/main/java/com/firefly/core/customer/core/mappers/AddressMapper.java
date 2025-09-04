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

import com.firefly.core.customer.interfaces.dtos.AddressDTO;
import com.firefly.core.customer.interfaces.dtos.NaturalPersonDTO;
import com.firefly.core.customer.models.entities.Address;
import com.firefly.core.customer.models.entities.NaturalPerson;
import org.mapstruct.*;

/**
 * MapStruct mapper for converting between Address entity and AddressDTO.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper {

    /**
     * Converts Address entity to AddressDTO.
     *
     * @param address the Address entity to convert
     * @return the converted AddressDTO
     */
    AddressDTO toDTO(Address address);

    /**
     * Converts AddressDTO to Address entity.
     *
     * @param addressDTO the AddressDTO to convert
     * @return the converted Address entity
     */
    Address toEntity(AddressDTO addressDTO);

    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AddressDTO dto, @MappingTarget Address entity);

}