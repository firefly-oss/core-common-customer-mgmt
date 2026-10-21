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


package io.fireflysoftware.core.customer.web.controllers;

import io.fireflysoftware.core.customer.core.services.PartyIndustryClassificationService;
import io.fireflysoftware.core.customer.interfaces.dtos.PartyIndustryClassificationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parties/{partyId}/industry-classifications")
@RequiredArgsConstructor
@Validated
@Tag(name = "Industry classifications", description = "Scheme and code classifications for a party. At most one primary.")
public class PartyIndustryClassificationController {

    private final PartyIndustryClassificationService service;

    @PostMapping("/filter")
    @Operation(summary = "Filter industry classifications")
    public Mono<ResponseEntity<PaginationResponse<PartyIndustryClassificationDTO>>> filter(
            @PathVariable UUID partyId,
            @Valid @RequestBody FilterRequest<PartyIndustryClassificationDTO> filterRequest) {
        return service.filter(partyId, filterRequest).map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Add an industry classification")
    public Mono<ResponseEntity<PartyIndustryClassificationDTO>> create(
            @PathVariable UUID partyId,
            @Valid @RequestBody PartyIndustryClassificationDTO dto) {
        return service.create(partyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{classificationId}")
    @Operation(summary = "Get an industry classification")
    public Mono<ResponseEntity<PartyIndustryClassificationDTO>> get(
            @PathVariable UUID partyId,
            @PathVariable UUID classificationId) {
        return service.get(partyId, classificationId).map(ResponseEntity::ok);
    }

    @PutMapping("/{classificationId}")
    @Operation(summary = "Replace an industry classification")
    public Mono<ResponseEntity<PartyIndustryClassificationDTO>> update(
            @PathVariable UUID partyId,
            @PathVariable UUID classificationId,
            @Valid @RequestBody PartyIndustryClassificationDTO dto) {
        return service.update(partyId, classificationId, dto).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{classificationId}")
    @Operation(summary = "Delete an industry classification")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable UUID partyId,
            @PathVariable UUID classificationId) {
        return service.delete(partyId, classificationId).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
