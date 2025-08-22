package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.ConsentMapper;
import com.catalis.core.customer.core.services.ConsentService;
import com.catalis.core.customer.interfaces.dtos.ConsentDTO;
import com.catalis.core.customer.models.entities.Consent;
import com.catalis.core.customer.models.repositories.ConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ConsentServiceImpl implements ConsentService {

    @Autowired
    private ConsentRepository repository;

    @Autowired
    private ConsentMapper mapper;

    @Override
    public Mono<PaginationResponse<ConsentDTO>> filterConsents(FilterRequest<ConsentDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Consent.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ConsentDTO> createConsent(ConsentDTO consentDTO) {
        return Mono.just(consentDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ConsentDTO> updateConsent(Long consentId, ConsentDTO consentDTO) {
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
    public Mono<Void> deleteConsent(Long consentId) {
        return repository.findById(consentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Consent not found with ID: " + consentId)))
                .flatMap(consent -> repository.deleteById(consentId));
    }

    @Override
    public Mono<ConsentDTO> getConsentById(Long consentId) {
        return repository.findById(consentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Consent not found with ID: " + consentId)))
                .map(mapper::toDTO);
    }
}