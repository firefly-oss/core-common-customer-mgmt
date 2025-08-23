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
    public Mono<PaginationResponse<AddressDTO>> filterAddresses(Long partyId, FilterRequest<AddressDTO> filterRequest) {
        // Add partyId filter to the existing filter request
        // For now, we'll delegate to FilterUtils but this could be enhanced to add partyId filtering
        return FilterUtils
                .createFilter(
                        Address.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
        // TODO: Enhance FilterUtils to support party-specific filtering
    }

    @Override
    public Mono<AddressDTO> createAddress(Long partyId, AddressDTO addressDTO) {
        return Mono.just(addressDTO)
                .doOnNext(dto -> dto.setPartyId(partyId)) // Ensure partyId is set
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AddressDTO> updateAddress(Long partyId, Long addressId, AddressDTO addressDTO) {
        return repository.findById(addressId)
                .switchIfEmpty(Mono.error(new RuntimeException("Address not found with ID: " + addressId)))
                .flatMap(existingAddress -> {
                    // Validate that the address belongs to the specified party
                    if (!partyId.equals(existingAddress.getPartyId())) {
                        return Mono.error(new RuntimeException("Address with ID " + addressId + " does not belong to party " + partyId));
                    }
                    Address updatedAddress = mapper.toEntity(addressDTO);
                    updatedAddress.setAddressId(addressId);
                    updatedAddress.setPartyId(partyId); // Ensure party relationship is maintained
                    return repository.save(updatedAddress);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteAddress(Long partyId, Long addressId) {
        return repository.findById(addressId)
                .switchIfEmpty(Mono.error(new RuntimeException("Address not found with ID: " + addressId)))
                .flatMap(address -> {
                    // Validate that the address belongs to the specified party
                    if (!partyId.equals(address.getPartyId())) {
                        return Mono.error(new RuntimeException("Address with ID " + addressId + " does not belong to party " + partyId));
                    }
                    return repository.deleteById(addressId);
                });
    }

    @Override
    public Mono<AddressDTO> getAddressById(Long partyId, Long addressId) {
        return repository.findById(addressId)
                .switchIfEmpty(Mono.error(new RuntimeException("Address not found with ID: " + addressId)))
                .flatMap(address -> {
                    // Validate that the address belongs to the specified party
                    if (!partyId.equals(address.getPartyId())) {
                        return Mono.error(new RuntimeException("Address with ID " + addressId + " does not belong to party " + partyId));
                    }
                    return Mono.just(mapper.toDTO(address));
                });
    }
}