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

import io.fireflysoftware.core.customer.core.services.ConsentService;
import io.fireflysoftware.core.customer.interfaces.dtos.ConsentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Stand-alone WebFlux test using {@code WebTestClient.bindToController(...)}
 * to avoid loading the full Spring context (and the framework's
 * {@code GlobalExceptionHandler}, which would require many additional collaborator
 * beans).
 */
@ExtendWith(MockitoExtension.class)
class ConsentControllerTest {

    @Mock
    private ConsentService consentService;

    private WebTestClient webTestClient;
    private UUID partyId;
    private UUID consentId;
    private UUID consentTypeId;
    private UUID applicationId;

    @BeforeEach
    void setUp() {
        partyId = UUID.randomUUID();
        consentId = UUID.randomUUID();
        consentTypeId = UUID.randomUUID();
        applicationId = UUID.randomUUID();
        webTestClient = WebTestClient.bindToController(new ConsentController(consentService))
                .build();
    }

    @Test
    void updateConsent_ShouldRoundTripApplicationId() {
        ConsentDTO request = ConsentDTO.builder()
                .partyId(partyId)
                .consentTypeId(consentTypeId)
                .granted(Boolean.TRUE)
                .applicationId(applicationId)
                .build();

        ConsentDTO updateResponse = ConsentDTO.builder()
                .consentId(consentId)
                .partyId(partyId)
                .consentTypeId(consentTypeId)
                .granted(Boolean.TRUE)
                .applicationId(applicationId)
                .build();

        when(consentService.updateConsent(eq(partyId), eq(consentId), any(ConsentDTO.class)))
                .thenReturn(Mono.just(updateResponse));

        webTestClient.put()
                .uri("/api/v1/parties/{partyId}/consents/{consentId}", partyId, consentId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.applicationId").isEqualTo(applicationId.toString());

        when(consentService.getConsentById(partyId, consentId))
                .thenReturn(Mono.just(updateResponse));

        webTestClient.get()
                .uri("/api/v1/parties/{partyId}/consents/{consentId}", partyId, consentId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.applicationId").isEqualTo(applicationId.toString())
                .jsonPath("$.granted").isEqualTo(true);
    }
}
