package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.IdentityDocumentMapper;
import com.firefly.core.customer.core.services.IdentityDocumentService;
import com.firefly.core.customer.interfaces.dtos.IdentityDocumentDTO;
import com.firefly.core.customer.models.entities.IdentityDocument;
import com.firefly.core.customer.models.repositories.IdentityDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class IdentityDocumentServiceImpl implements IdentityDocumentService {

    @Autowired
    private IdentityDocumentRepository repository;

    @Autowired
    private IdentityDocumentMapper mapper;

    @Override
    public Mono<PaginationResponse<IdentityDocumentDTO>> filterIdentityDocuments(UUID partyId, FilterRequest<IdentityDocumentDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        IdentityDocument.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<IdentityDocumentDTO> createIdentityDocument(UUID partyId, IdentityDocumentDTO identityDocumentDTO) {
        return Mono.just(identityDocumentDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<IdentityDocumentDTO> updateIdentityDocument(UUID partyId, UUID identityDocumentId, IdentityDocumentDTO identityDocumentDTO) {
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
    public Mono<Void> deleteIdentityDocument(UUID partyId, UUID identityDocumentId) {
        return repository.findById(identityDocumentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document not found with ID: " + identityDocumentId)))
                .flatMap(identityDocument -> repository.deleteById(identityDocumentId));
    }

    @Override
    public Mono<IdentityDocumentDTO> getIdentityDocumentById(UUID partyId, UUID identityDocumentId) {
        return repository.findById(identityDocumentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Identity document not found with ID: " + identityDocumentId)))
                .map(mapper::toDTO);
    }
}