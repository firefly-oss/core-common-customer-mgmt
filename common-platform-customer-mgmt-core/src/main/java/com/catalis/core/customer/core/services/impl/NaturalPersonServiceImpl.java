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
    public Mono<PaginationResponse<NaturalPersonDTO>> filterNaturalPersons(Long partyId, FilterRequest<NaturalPersonDTO> filterRequest) {
        // Add partyId filter to the existing filter request
        // For now, we'll delegate to FilterUtils but this could be enhanced to add partyId filtering
        return FilterUtils
                .createFilter(
                        NaturalPerson.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
        // TODO: Enhance FilterUtils to support party-specific filtering
    }

    @Override
    public Mono<NaturalPersonDTO> createNaturalPerson(Long partyId, NaturalPersonDTO naturalPersonDTO) {
        return Mono.just(naturalPersonDTO)
                .doOnNext(dto -> dto.setPartyId(partyId)) // Ensure partyId is set
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<NaturalPersonDTO> updateNaturalPerson(Long partyId, Long naturalPersonId, NaturalPersonDTO naturalPersonDTO) {
        return repository.findById(naturalPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Natural person not found with ID: " + naturalPersonId)))
                .flatMap(existingNaturalPerson -> {
                    // Validate that the natural person belongs to the specified party
                    if (!partyId.equals(existingNaturalPerson.getPartyId())) {
                        return Mono.error(new RuntimeException("Natural person with ID " + naturalPersonId + " does not belong to party " + partyId));
                    }
                    mapper.updateEntityFromDto(naturalPersonDTO, existingNaturalPerson);
                    return repository.save(existingNaturalPerson);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteNaturalPerson(Long partyId, Long naturalPersonId) {
        return repository.findById(naturalPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Natural person not found with ID: " + naturalPersonId)))
                .flatMap(naturalPerson -> {
                    // Validate that the natural person belongs to the specified party
                    if (!partyId.equals(naturalPerson.getPartyId())) {
                        return Mono.error(new RuntimeException("Natural person with ID " + naturalPersonId + " does not belong to party " + partyId));
                    }
                    return repository.deleteById(naturalPersonId);
                });
    }

    @Override
    public Mono<NaturalPersonDTO> getNaturalPersonById(Long partyId, Long naturalPersonId) {
        return repository.findById(naturalPersonId)
                .switchIfEmpty(Mono.error(new RuntimeException("Natural person not found with ID: " + naturalPersonId)))
                .flatMap(naturalPerson -> {
                    // Validate that the natural person belongs to the specified party
                    if (!partyId.equals(naturalPerson.getPartyId())) {
                        return Mono.error(new RuntimeException("Natural person with ID " + naturalPersonId + " does not belong to party " + partyId));
                    }
                    return Mono.just(mapper.toDTO(naturalPerson));
                });
    }

    @Override
    public Mono<NaturalPersonDTO> getNaturalPersonByPartyId(Long partyId) {
        return repository.findByPartyId(partyId)
                .map(mapper::toDTO)
                .next();
    }
}