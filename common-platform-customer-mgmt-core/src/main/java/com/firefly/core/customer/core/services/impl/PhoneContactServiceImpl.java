/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.customer.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
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