package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.EmailContactMapper;
import com.catalis.core.customer.core.services.EmailContactService;
import com.catalis.core.customer.interfaces.dtos.EmailContactDTO;
import com.catalis.core.customer.models.entities.EmailContact;
import com.catalis.core.customer.models.repositories.EmailContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class EmailContactServiceImpl implements EmailContactService {

    @Autowired
    private EmailContactRepository repository;

    @Autowired
    private EmailContactMapper mapper;

    @Override
    public Mono<PaginationResponse<EmailContactDTO>> filterEmailContacts(Long partyId, FilterRequest<EmailContactDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        EmailContact.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<EmailContactDTO> createEmailContact(Long partyId, EmailContactDTO emailContactDTO) {
        return Mono.just(emailContactDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<EmailContactDTO> updateEmailContact(Long partyId, Long emailContactId, EmailContactDTO emailContactDTO) {
        return repository.findById(emailContactId)
                .switchIfEmpty(Mono.error(new RuntimeException("Email contact not found with ID: " + emailContactId)))
                .flatMap(existingEmailContact -> {
                    EmailContact updatedEmailContact = mapper.toEntity(emailContactDTO);
                    updatedEmailContact.setEmailContactId(emailContactId);
                    return repository.save(updatedEmailContact);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteEmailContact(Long partyId, Long emailContactId) {
        return repository.findById(emailContactId)
                .switchIfEmpty(Mono.error(new RuntimeException("Email contact not found with ID: " + emailContactId)))
                .flatMap(emailContact -> repository.deleteById(emailContactId));
    }

    @Override
    public Mono<EmailContactDTO> getEmailContactById(Long partyId, Long emailContactId) {
        return repository.findById(emailContactId)
                .switchIfEmpty(Mono.error(new RuntimeException("Email contact not found with ID: " + emailContactId)))
                .map(mapper::toDTO);
    }
}