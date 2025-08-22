package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PhoneContactMapper;
import com.catalis.core.customer.core.services.PhoneContactService;
import com.catalis.core.customer.interfaces.dtos.PhoneContactDTO;
import com.catalis.core.customer.models.entities.PhoneContact;
import com.catalis.core.customer.models.repositories.PhoneContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PhoneContactServiceImpl implements PhoneContactService {

    @Autowired
    private PhoneContactRepository repository;

    @Autowired
    private PhoneContactMapper mapper;

    @Override
    public Mono<PaginationResponse<PhoneContactDTO>> filterPhoneContacts(FilterRequest<PhoneContactDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PhoneContact.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PhoneContactDTO> createPhoneContact(PhoneContactDTO phoneContactDTO) {
        return Mono.just(phoneContactDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PhoneContactDTO> updatePhoneContact(Long phoneContactId, PhoneContactDTO phoneContactDTO) {
        return repository.findById(phoneContactId)
                .switchIfEmpty(Mono.error(new RuntimeException("Phone contact not found with ID: " + phoneContactId)))
                .flatMap(existingPhoneContact -> {
                    PhoneContact updatedPhoneContact = mapper.toEntity(phoneContactDTO);
                    updatedPhoneContact.setPhoneContactId(phoneContactId);
                    return repository.save(updatedPhoneContact);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePhoneContact(Long phoneContactId) {
        return repository.findById(phoneContactId)
                .switchIfEmpty(Mono.error(new RuntimeException("Phone contact not found with ID: " + phoneContactId)))
                .flatMap(phoneContact -> repository.deleteById(phoneContactId));
    }

    @Override
    public Mono<PhoneContactDTO> getPhoneContactById(Long phoneContactId) {
        return repository.findById(phoneContactId)
                .switchIfEmpty(Mono.error(new RuntimeException("Phone contact not found with ID: " + phoneContactId)))
                .map(mapper::toDTO);
    }
}