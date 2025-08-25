package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PoliticallyExposedPersonMapper;
import com.catalis.core.customer.core.services.PoliticallyExposedPersonService;
import com.catalis.core.customer.interfaces.dtos.PoliticallyExposedPersonDTO;
import com.catalis.core.customer.models.entities.PoliticallyExposedPerson;
import com.catalis.core.customer.models.repositories.PoliticallyExposedPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PoliticallyExposedPersonServiceImpl implements PoliticallyExposedPersonService {

    @Autowired
    private PoliticallyExposedPersonRepository repository;

    @Autowired
    private PoliticallyExposedPersonMapper mapper;

    @Override
    public Mono<PaginationResponse<PoliticallyExposedPersonDTO>> filterPoliticallyExposedPersons(Long partyId, FilterRequest<PoliticallyExposedPersonDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PoliticallyExposedPerson.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PoliticallyExposedPersonDTO> createPoliticallyExposedPerson(Long partyId, PoliticallyExposedPersonDTO politicallyExposedPersonDTO) {
        return Mono.just(politicallyExposedPersonDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PoliticallyExposedPersonDTO> updatePoliticallyExposedPerson(Long partyId, Long politicallyExposedPersonId, PoliticallyExposedPersonDTO politicallyExposedPersonDTO) {
        return repository.findById(politicallyExposedPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Politically exposed person not found with ID: " + politicallyExposedPersonId)))
                .flatMap(existingPoliticallyExposedPerson -> {
                    PoliticallyExposedPerson updatedPoliticallyExposedPerson = mapper.toEntity(politicallyExposedPersonDTO);
                    updatedPoliticallyExposedPerson.setPepId(politicallyExposedPersonId);
                    return repository.save(updatedPoliticallyExposedPerson);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePoliticallyExposedPerson(Long partyId, Long politicallyExposedPersonId) {
        return repository.findById(politicallyExposedPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Politically exposed person not found with ID: " + politicallyExposedPersonId)))
                .flatMap(politicallyExposedPerson -> repository.deleteById(politicallyExposedPersonId));
    }

    @Override
    public Mono<PoliticallyExposedPersonDTO> getPoliticallyExposedPersonById(Long partyId, Long politicallyExposedPersonId) {
        return repository.findById(politicallyExposedPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Politically exposed person not found with ID: " + politicallyExposedPersonId)))
                .map(mapper::toDTO);
    }
}