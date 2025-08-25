package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.IdentityDocumentMapper;
import com.catalis.core.customer.core.services.IdentityDocumentService;
import com.catalis.core.customer.interfaces.dtos.IdentityDocumentDTO;
import com.catalis.core.customer.models.entities.IdentityDocument;
import com.catalis.core.customer.models.repositories.IdentityDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class IdentityDocumentServiceImpl implements IdentityDocumentService {

    @Autowired
    private IdentityDocumentRepository repository;

    @Autowired
    private IdentityDocumentMapper mapper;

    @Override
    public Mono<PaginationResponse<IdentityDocumentDTO>> filterIdentityDocuments(Long partyId, FilterRequest<IdentityDocumentDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        IdentityDocument.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<IdentityDocumentDTO> createIdentityDocument(Long partyId, IdentityDocumentDTO identityDocumentDTO) {
        return Mono.just(identityDocumentDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<IdentityDocumentDTO> updateIdentityDocument(Long partyId, Long identityDocumentId, IdentityDocumentDTO identityDocumentDTO) {
        return repository.findById(identityDocumentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document not found with ID: " + identityDocumentId)))
                .flatMap(existingIdentityDocument -> {
                    IdentityDocument updatedIdentityDocument = mapper.toEntity(identityDocumentDTO);
                    updatedIdentityDocument.setIdentityDocumentId(identityDocumentId);
                    return repository.save(updatedIdentityDocument);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteIdentityDocument(Long partyId, Long identityDocumentId) {
        return repository.findById(identityDocumentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document not found with ID: " + identityDocumentId)))
                .flatMap(identityDocument -> repository.deleteById(identityDocumentId));
    }

    @Override
    public Mono<IdentityDocumentDTO> getIdentityDocumentById(Long partyId, Long identityDocumentId) {
        return repository.findById(identityDocumentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document not found with ID: " + identityDocumentId)))
                .map(mapper::toDTO);
    }
}