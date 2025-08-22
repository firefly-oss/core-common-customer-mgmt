package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.NaturalPersonMapper;
import com.catalis.core.customer.core.services.NaturalPersonService;
import com.catalis.core.customer.interfaces.dtos.NaturalPersonDTO;
import com.catalis.core.customer.models.entities.NaturalPerson;
import com.catalis.core.customer.models.repositories.NaturalPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class NaturalPersonServiceImpl implements NaturalPersonService {

    @Autowired
    private NaturalPersonRepository repository;

    @Autowired
    private NaturalPersonMapper mapper;

    @Override
    public Mono<PaginationResponse<NaturalPersonDTO>> filterNaturalPersons(FilterRequest<NaturalPersonDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        NaturalPerson.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<NaturalPersonDTO> createNaturalPerson(NaturalPersonDTO naturalPersonDTO) {
        return Mono.just(naturalPersonDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<NaturalPersonDTO> updateNaturalPerson(Long naturalPersonId, NaturalPersonDTO naturalPersonDTO) {
        return repository.findById(naturalPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Natural person not found with ID: " + naturalPersonId)))
                .flatMap(existingNaturalPerson -> {
                    NaturalPerson updatedNaturalPerson = mapper.toEntity(naturalPersonDTO);
                    updatedNaturalPerson.setNaturalPersonId(naturalPersonId);
                    return repository.save(updatedNaturalPerson);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteNaturalPerson(Long naturalPersonId) {
        return repository.findById(naturalPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Natural person not found with ID: " + naturalPersonId)))
                .flatMap(naturalPerson -> repository.deleteById(naturalPersonId));
    }

    @Override
    public Mono<NaturalPersonDTO> getNaturalPersonById(Long naturalPersonId) {
        return repository.findById(naturalPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Natural person not found with ID: " + naturalPersonId)))
                .map(mapper::toDTO);
    }
}