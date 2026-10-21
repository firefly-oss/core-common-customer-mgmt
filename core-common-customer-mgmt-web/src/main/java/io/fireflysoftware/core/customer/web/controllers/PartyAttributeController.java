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

import io.fireflysoftware.core.customer.core.services.PartyAttributeService;
import io.fireflysoftware.core.customer.interfaces.dtos.PartyAttributeDTO;
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
@RequestMapping("/api/v1/parties/{partyId}/attributes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Party attributes", description = "Effective-dated facts for any party. Employment, housing, loans, and debts live here.")
public class PartyAttributeController {

    private final PartyAttributeService service;

    @PostMapping("/filter")
    @Operation(summary = "Filter party attributes")
    public Mono<ResponseEntity<PaginationResponse<PartyAttributeDTO>>> filter(
            @PathVariable UUID partyId,
            @Valid @RequestBody FilterRequest<PartyAttributeDTO> filterRequest) {
        return service.filter(partyId, filterRequest).map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Store a party attribute")
    public Mono<ResponseEntity<PartyAttributeDTO>> create(
            @PathVariable UUID partyId,
            @Valid @RequestBody PartyAttributeDTO dto) {
        return service.create(partyId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{attributeId}")
    @Operation(summary = "Get a party attribute")
    public Mono<ResponseEntity<PartyAttributeDTO>> get(
            @PathVariable UUID partyId,
            @PathVariable UUID attributeId) {
        return service.get(partyId, attributeId).map(ResponseEntity::ok);
    }

    @PutMapping("/{attributeId}")
    @Operation(summary = "Replace a party attribute")
    public Mono<ResponseEntity<PartyAttributeDTO>> update(
            @PathVariable UUID partyId,
            @PathVariable UUID attributeId,
            @Valid @RequestBody PartyAttributeDTO dto) {
        return service.update(partyId, attributeId, dto).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{attributeId}")
    @Operation(summary = "Delete a party attribute")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable UUID partyId,
            @PathVariable UUID attributeId) {
        return service.delete(partyId, attributeId).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
