package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.AddressMapper;
import com.firefly.core.customer.core.services.AddressService;
import com.firefly.core.customer.interfaces.dtos.AddressDTO;
import com.firefly.core.customer.models.entities.Address;
import com.firefly.core.customer.models.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private AddressMapper mapper;

    @Override
    public Mono<PaginationResponse<AddressDTO>> filterAddresses(UUID partyId, FilterRequest<AddressDTO> filterRequest) {
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
    public Mono<AddressDTO> createAddress(UUID partyId, AddressDTO addressDTO) {
        return Mono.just(addressDTO)
                .doOnNext(dto -> dto.setPartyId(partyId)) // Ensure partyId is set
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AddressDTO> updateAddress(UUID partyId, UUID addressId, AddressDTO addressDTO) {
        return repository.findById(addressId)
                .switchIfEmpty(Mono.error(new RuntimeException("Address not found with ID: " + addressId)))
                .flatMap(existingAddress -> {
                    // Validate that the address belongs to the specified party
                    if (!partyId.equals(existingAddress.getPartyId())) {
                        return Mono.error(new RuntimeException("Address with ID " + addressId + " does not belong to party " + partyId));
                    }
                    mapper.updateEntityFromDto(addressDTO, existingAddress);
                    return repository.save(existingAddress);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteAddress(UUID partyId, UUID addressId) {
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
    public Mono<AddressDTO> getAddressById(UUID partyId, UUID addressId) {
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