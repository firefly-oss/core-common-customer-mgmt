/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.customer.core.services;

import io.fireflysoftware.core.customer.interfaces.dtos.PartyAttributeDTO;
import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PartyAttributeService {

    Mono<PaginationResponse<PartyAttributeDTO>> filter(UUID partyId, FilterRequest<PartyAttributeDTO> filterRequest);

    Mono<PartyAttributeDTO> create(UUID partyId, PartyAttributeDTO dto);

    Mono<PartyAttributeDTO> update(UUID partyId, UUID attributeId, PartyAttributeDTO dto);

    Mono<Void> delete(UUID partyId, UUID attributeId);

    Mono<PartyAttributeDTO> get(UUID partyId, UUID attributeId);
}
