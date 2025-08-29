package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.EmailContactMapper;
import com.firefly.core.customer.core.services.EmailContactService;
import com.firefly.core.customer.interfaces.dtos.EmailContactDTO;
import com.firefly.core.customer.models.entities.EmailContact;
import com.firefly.core.customer.models.repositories.EmailContactRepository;
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
                    // Validate that the natural person belongs to the specified party
                    if (!partyId.equals(existingEmailContact.getPartyId())) {
                        return Mono.error(new RuntimeException("Email contact with ID " + emailContactId + " does not belong to party " + partyId));
                    }
                    mapper.updateEntityFromDto(emailContactDTO, existingEmailContact);
                    return repository.save(existingEmailContact);
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