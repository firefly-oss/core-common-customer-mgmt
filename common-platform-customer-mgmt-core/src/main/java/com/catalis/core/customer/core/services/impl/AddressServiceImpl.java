package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.AddressMapper;
import com.catalis.core.customer.core.services.AddressService;
import com.catalis.core.customer.interfaces.dtos.AddressDTO;
import com.catalis.core.customer.models.entities.Address;
import com.catalis.core.customer.models.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private AddressMapper mapper;

    @Override
    public Mono<PaginationResponse<AddressDTO>> filterAddresses(FilterRequest<AddressDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Address.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<AddressDTO> createAddress(AddressDTO addressDTO) {
        return Mono.just(addressDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AddressDTO> updateAddress(Long addressId, AddressDTO addressDTO) {
        return repository.findById(addressId)
                .switchIfEmpty(Mono.error(new RuntimeException("Address not found with ID: " + addressId)))
                .flatMap(existingAddress -> {
                    Address updatedAddress = mapper.toEntity(addressDTO);
                    updatedAddress.setAddressId(addressId);
                    return repository.save(updatedAddress);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteAddress(Long addressId) {
        return repository.findById(addressId)
                .switchIfEmpty(Mono.error(new RuntimeException("Address not found with ID: " + addressId)))
                .flatMap(address -> repository.deleteById(addressId));
    }

    @Override
    public Mono<AddressDTO> getAddressById(Long addressId) {
        return repository.findById(addressId)
                .switchIfEmpty(Mono.error(new RuntimeException("Address not found with ID: " + addressId)))
                .map(mapper::toDTO);
    }
}