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

import io.fireflysoftware.core.customer.interfaces.dtos.PartyIndustryClassificationDTO;
import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PartyIndustryClassificationService {

    Mono<PaginationResponse<PartyIndustryClassificationDTO>> filter(UUID partyId, FilterRequest<PartyIndustryClassificationDTO> filterRequest);

    Mono<PartyIndustryClassificationDTO> create(UUID partyId, PartyIndustryClassificationDTO dto);

    Mono<PartyIndustryClassificationDTO> update(UUID partyId, UUID classificationId, PartyIndustryClassificationDTO dto);

    Mono<Void> delete(UUID partyId, UUID classificationId);

    Mono<PartyIndustryClassificationDTO> get(UUID partyId, UUID classificationId);
}
