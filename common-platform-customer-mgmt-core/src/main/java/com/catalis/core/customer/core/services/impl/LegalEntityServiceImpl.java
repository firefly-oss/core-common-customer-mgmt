package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.LegalEntityMapper;
import com.catalis.core.customer.core.services.LegalEntityService;
import com.catalis.core.customer.interfaces.dtos.LegalEntityDTO;
import com.catalis.core.customer.models.entities.LegalEntity;
import com.catalis.core.customer.models.repositories.LegalEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class LegalEntityServiceImpl implements LegalEntityService {

    @Autowired
    private LegalEntityRepository repository;

    @Autowired
    private LegalEntityMapper mapper;

    @Override
    public Mono<PaginationResponse<LegalEntityDTO>> filterLegalEntities(FilterRequest<LegalEntityDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        LegalEntity.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<LegalEntityDTO> createLegalEntity(Long partyId, LegalEntityDTO legalEntityDTO) {
        return Mono.just(legalEntityDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LegalEntityDTO> updateLegalEntity(Long partyId, Long legalEntityId, LegalEntityDTO legalEntityDTO) {
        return repository.findById(legalEntityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Legal entity not found with ID: " + legalEntityId)))
                .flatMap(existingLegalEntity -> {
                    mapper.updateEntityFromDto(legalEntityDTO, existingLegalEntity);
                    return repository.save(existingLegalEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteLegalEntity(Long partyId, Long legalEntityId) {
        return repository.findById(legalEntityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Legal entity not found with ID: " + legalEntityId)))
                .flatMap(legalEntity -> repository.deleteById(legalEntityId));
    }

    @Override
    public Mono<LegalEntityDTO> getLegalEntityById(Long partyId, Long legalEntityId) {
        return repository.findById(legalEntityId)
                .switchIfEmpty(Mono.error(new RuntimeException("Legal entity not found with ID: " + legalEntityId)))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LegalEntityDTO> getLegalEntityByPartyId(Long partyId) {
        return repository.findByPartyId(partyId)
                .map(mapper::toDTO)
                .next();
    }
}