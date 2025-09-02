package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.ConsentMapper;
import com.firefly.core.customer.core.services.ConsentService;
import com.firefly.core.customer.interfaces.dtos.ConsentDTO;
import com.firefly.core.customer.models.entities.Consent;
import com.firefly.core.customer.models.repositories.ConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class ConsentServiceImpl implements ConsentService {

    @Autowired
    private ConsentRepository repository;

    @Autowired
    private ConsentMapper mapper;

    @Override
    public Mono<PaginationResponse<ConsentDTO>> filterConsents(UUID partyId, FilterRequest<ConsentDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Consent.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ConsentDTO> createConsent(UUID partyId, ConsentDTO consentDTO) {
        return Mono.just(consentDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ConsentDTO> updateConsent(UUID partyId, UUID consentId, ConsentDTO consentDTO) {
        return repository.findById(consentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Consent not found with ID: " + consentId)))
                .flatMap(existingConsent -> {
                    Consent updatedConsent = mapper.toEntity(consentDTO);
                    updatedConsent.setConsentId(consentId);
                    return repository.save(updatedConsent);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteConsent(UUID partyId, UUID consentId) {
        return repository.findById(consentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Consent not found with ID: " + consentId)))
                .flatMap(consent -> repository.deleteById(consentId));
    }

    @Override
    public Mono<ConsentDTO> getConsentById(UUID partyId, UUID consentId) {
        return repository.findById(consentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Consent not found with ID: " + consentId)))
                .map(mapper::toDTO);
    }
}