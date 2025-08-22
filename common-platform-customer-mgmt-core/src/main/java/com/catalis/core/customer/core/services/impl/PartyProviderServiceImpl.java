package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PartyProviderMapper;
import com.catalis.core.customer.core.services.PartyProviderService;
import com.catalis.core.customer.interfaces.dtos.PartyProviderDTO;
import com.catalis.core.customer.models.entities.PartyProvider;
import com.catalis.core.customer.models.repositories.PartyProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PartyProviderServiceImpl implements PartyProviderService {

    @Autowired
    private PartyProviderRepository repository;

    @Autowired
    private PartyProviderMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyProviderDTO>> filterPartyProviders(FilterRequest<PartyProviderDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyProvider.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyProviderDTO> createPartyProvider(PartyProviderDTO partyProviderDTO) {
        return Mono.just(partyProviderDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyProviderDTO> updatePartyProvider(Long partyProviderId, PartyProviderDTO partyProviderDTO) {
        return repository.findById(partyProviderId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party provider not found with ID: " + partyProviderId)))
                .flatMap(existingPartyProvider -> {
                    PartyProvider updatedPartyProvider = mapper.toEntity(partyProviderDTO);
                    updatedPartyProvider.setPartyProviderId(partyProviderId);
                    return repository.save(updatedPartyProvider);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePartyProvider(Long partyProviderId) {
        return repository.findById(partyProviderId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party provider not found with ID: " + partyProviderId)))
                .flatMap(partyProvider -> repository.deleteById(partyProviderId));
    }

    @Override
    public Mono<PartyProviderDTO> getPartyProviderById(Long partyProviderId) {
        return repository.findById(partyProviderId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party provider not found with ID: " + partyProviderId)))
                .map(mapper::toDTO);
    }
}