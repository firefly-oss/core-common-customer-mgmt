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

package io.fireflysoftware.core.customer.web.controllers;

import io.fireflysoftware.core.customer.core.services.LegalEntityService;
import io.fireflysoftware.core.customer.interfaces.dtos.LegalEntityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Stand-alone WebFlux test using {@code WebTestClient.bindToController(...)}
 * to avoid loading the full Spring context (and the framework's
 * {@code GlobalExceptionHandler}, which would require many additional collaborator
 * beans). Validation behaviour (Bean Validation triggering 400 on invalid bodies)
 * is preserved because Spring's WebFlux dispatcher still processes {@code @Valid}.
 */
@ExtendWith(MockitoExtension.class)
class LegalEntityControllerTest {

    @Mock
    private LegalEntityService legalEntityService;

    private WebTestClient webTestClient;
    private UUID partyId;

    @BeforeEach
    void setUp() {
        partyId = UUID.randomUUID();
        webTestClient = WebTestClient.bindToController(new LegalEntityController(legalEntityService))
                .build();
    }

    @Test
    void createLegalEntity_ShouldPersistAllNewCompanyDataFields() {
        LegalEntityDTO request = LegalEntityDTO.builder()
                .partyId(partyId)
                .legalName("Acme Corp")
                .employeeRange("26-50")
                .annualRevenue(new BigDecimal("1000000.00"))
                .contactName("Jane Director")
                .contactPosition("CFO")
                .contactEmail("jane@acme.example")
                .contactPhone("+34 600 000 000")
                .build();

        LegalEntityDTO response = LegalEntityDTO.builder()
                .legalEntityId(UUID.randomUUID())
                .partyId(partyId)
                .legalName("Acme Corp")
                .employeeRange("26-50")
                .annualRevenue(new BigDecimal("1000000.00"))
                .contactName("Jane Director")
                .contactPosition("CFO")
                .contactEmail("jane@acme.example")
                .contactPhone("+34 600 000 000")
                .build();

        when(legalEntityService.createLegalEntity(eq(partyId), any(LegalEntityDTO.class)))
                .thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/api/v1/parties/{partyId}/legal-entities", partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.employeeRange").isEqualTo("26-50")
                .jsonPath("$.annualRevenue").isEqualTo(1000000.00)
                .jsonPath("$.contactName").isEqualTo("Jane Director")
                .jsonPath("$.contactPosition").isEqualTo("CFO")
                .jsonPath("$.contactEmail").isEqualTo("jane@acme.example")
                .jsonPath("$.contactPhone").isEqualTo("+34 600 000 000");
    }

    @Test
    void createLegalEntity_ShouldRejectInvalidEmployeeRange() {
        LegalEntityDTO request = LegalEntityDTO.builder()
                .partyId(partyId)
                .legalName("Acme Corp")
                .employeeRange("invalid-range")
                .build();

        webTestClient.post()
                .uri("/api/v1/parties/{partyId}/legal-entities", partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void createLegalEntity_ShouldRejectInvalidContactEmail() {
        LegalEntityDTO request = LegalEntityDTO.builder()
                .partyId(partyId)
                .legalName("Acme Corp")
                .contactEmail("not-an-email")
                .build();

        webTestClient.post()
                .uri("/api/v1/parties/{partyId}/legal-entities", partyId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
