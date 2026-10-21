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

import io.fireflysoftware.core.customer.core.services.PartyRelationshipService;
import io.fireflysoftware.core.customer.interfaces.dtos.PartyAncestorDTO;
import io.fireflysoftware.core.customer.interfaces.dtos.PartyRelationshipDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parties/{partyId}")
@RequiredArgsConstructor
@Tag(name = "Party hierarchy", description = "Direct owners, direct subsidiaries, and ancestors of a legal entity")
public class PartyHierarchyController {

    private final PartyRelationshipService partyRelationshipService;

    @GetMapping("/owners")
    @Operation(summary = "Direct owners of a legal entity",
            description = "Ownership edges whose target is this legal entity. A natural person or another legal entity may own it. Percentages do not have to sum to 100.")
    public Mono<ResponseEntity<List<PartyRelationshipDTO>>> directOwners(
            @Parameter(description = "Party id of the legal entity", required = true) @PathVariable UUID partyId) {
        return partyRelationshipService.directOwners(partyId).map(ResponseEntity::ok);
    }

    @GetMapping("/subsidiaries")
    @Operation(summary = "Direct subsidiaries of a legal entity",
            description = "Children stored as PARENT, plus companies this legal entity owns. Sister companies are the other subsidiaries of the same parent and are not stored.")
    public Mono<ResponseEntity<List<PartyRelationshipDTO>>> directSubsidiaries(
            @Parameter(description = "Party id of the parent legal entity", required = true) @PathVariable UUID partyId) {
        return partyRelationshipService.directSubsidiaries(partyId).map(ResponseEntity::ok);
    }

    @GetMapping("/ancestors")
    @Operation(summary = "Ancestors of a legal entity",
            description = "Walks parent edges upward. Depth is capped at 25.")
    public Mono<ResponseEntity<List<PartyAncestorDTO>>> ancestors(
            @Parameter(description = "Party id of the legal entity", required = true) @PathVariable UUID partyId,
            @RequestParam(defaultValue = "25") int depth) {
        return partyRelationshipService.ancestors(partyId, depth).map(ResponseEntity::ok);
    }
}
