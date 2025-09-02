package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PoliticallyExposedPersonMapper;
import com.firefly.core.customer.core.services.PoliticallyExposedPersonService;
import com.firefly.core.customer.interfaces.dtos.PoliticallyExposedPersonDTO;
import com.firefly.core.customer.models.entities.PoliticallyExposedPerson;
import com.firefly.core.customer.models.repositories.PoliticallyExposedPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class PoliticallyExposedPersonServiceImpl implements PoliticallyExposedPersonService {

    @Autowired
    private PoliticallyExposedPersonRepository repository;

    @Autowired
    private PoliticallyExposedPersonMapper mapper;

    @Override
    public Mono<PaginationResponse<PoliticallyExposedPersonDTO>> filterPoliticallyExposedPersons(UUID partyId, FilterRequest<PoliticallyExposedPersonDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PoliticallyExposedPerson.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PoliticallyExposedPersonDTO> createPoliticallyExposedPerson(UUID partyId, PoliticallyExposedPersonDTO politicallyExposedPersonDTO) {
        return Mono.just(politicallyExposedPersonDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PoliticallyExposedPersonDTO> updatePoliticallyExposedPerson(UUID partyId, UUID politicallyExposedPersonId, PoliticallyExposedPersonDTO politicallyExposedPersonDTO) {
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
    public Mono<Void> deletePoliticallyExposedPerson(UUID partyId, UUID politicallyExposedPersonId) {
        return repository.findById(politicallyExposedPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Politically exposed person not found with ID: " + politicallyExposedPersonId)))
                .flatMap(politicallyExposedPerson -> repository.deleteById(politicallyExposedPersonId));
    }

    @Override
    public Mono<PoliticallyExposedPersonDTO> getPoliticallyExposedPersonById(UUID partyId, UUID politicallyExposedPersonId) {
        return repository.findById(politicallyExposedPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Politically exposed person not found with ID: " + politicallyExposedPersonId)))
                .map(mapper::toDTO);
    }
}