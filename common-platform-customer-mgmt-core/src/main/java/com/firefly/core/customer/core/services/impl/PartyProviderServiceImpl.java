package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PartyProviderMapper;
import com.firefly.core.customer.core.services.PartyProviderService;
import com.firefly.core.customer.interfaces.dtos.PartyProviderDTO;
import com.firefly.core.customer.models.entities.PartyProvider;
import com.firefly.core.customer.models.repositories.PartyProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class PartyProviderServiceImpl implements PartyProviderService {

    @Autowired
    private PartyProviderRepository repository;

    @Autowired
    private PartyProviderMapper mapper;

    @Override
    public Mono<PaginationResponse<PartyProviderDTO>> filterPartyProviders(UUID partyId, FilterRequest<PartyProviderDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PartyProvider.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PartyProviderDTO> createPartyProvider(UUID partyId, PartyProviderDTO partyProviderDTO) {
        return Mono.just(partyProviderDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PartyProviderDTO> updatePartyProvider(UUID partyId, UUID partyProviderId, PartyProviderDTO partyProviderDTO) {
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
    public Mono<Void> deletePartyProvider(UUID partyId, UUID partyProviderId) {
        return repository.findById(partyProviderId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party provider not found with ID: " + partyProviderId)))
                .flatMap(partyProvider -> repository.deleteById(partyProviderId));
    }

    @Override
    public Mono<PartyProviderDTO> getPartyProviderById(UUID partyId, UUID partyProviderId) {
        return repository.findById(partyProviderId)
                .switchIfEmpty(Mono.error(new RuntimeException("Party provider not found with ID: " + partyProviderId)))
                .map(mapper::toDTO);
    }
}