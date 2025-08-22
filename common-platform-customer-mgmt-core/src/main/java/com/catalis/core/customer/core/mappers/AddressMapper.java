package com.catalis.core.customer.core.mappers;

import com.catalis.core.customer.interfaces.dtos.AddressDTO;
import com.catalis.core.customer.models.entities.Address;
import org.mapstruct.Mapper;

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
}