package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PhoneContactMapper;
import com.firefly.core.customer.core.services.PhoneContactService;
import com.firefly.core.customer.interfaces.dtos.PhoneContactDTO;
import com.firefly.core.customer.models.entities.PhoneContact;
import com.firefly.core.customer.models.repositories.PhoneContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class PhoneContactServiceImpl implements PhoneContactService {

    @Autowired
    private PhoneContactRepository repository;

    @Autowired
    private PhoneContactMapper mapper;

    @Override
    public Mono<PaginationResponse<PhoneContactDTO>> filterPhoneContacts(UUID partyId, FilterRequest<PhoneContactDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PhoneContact.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PhoneContactDTO> createPhoneContact(UUID partyId, PhoneContactDTO phoneContactDTO) {
        return Mono.just(phoneContactDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PhoneContactDTO> updatePhoneContact(UUID partyId, UUID phoneContactId, PhoneContactDTO phoneContactDTO) {
        return repository.findById(phoneContactId)
                .switchIfEmpty(Mono.error(new RuntimeException("Phone contact not found with ID: " + phoneContactId)))
                .flatMap(existingPhoneContact -> {
                    // Validate that the natural person belongs to the specified party
                    if (!partyId.equals(existingPhoneContact.getPartyId())) {
                        return Mono.error(new RuntimeException("Phone contact with ID " + phoneContactId + " does not belong to party " + partyId));
                    }
                    mapper.updateEntityFromDto(phoneContactDTO, existingPhoneContact);
                    return repository.save(existingPhoneContact);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePhoneContact(UUID partyId, UUID phoneContactId) {
        return repository.findById(phoneContactId)
                .switchIfEmpty(Mono.error(new RuntimeException("Phone contact not found with ID: " + phoneContactId)))
                .flatMap(phoneContact -> repository.deleteById(phoneContactId));
    }

    @Override
    public Mono<PhoneContactDTO> getPhoneContactById(UUID partyId, UUID phoneContactId) {
        return repository.findById(phoneContactId)
                .switchIfEmpty(Mono.error(new RuntimeException("Phone contact not found with ID: " + phoneContactId)))
                .map(mapper::toDTO);
    }
}