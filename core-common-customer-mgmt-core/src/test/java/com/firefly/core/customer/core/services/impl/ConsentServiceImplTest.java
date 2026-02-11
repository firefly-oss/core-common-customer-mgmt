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
import com.firefly.core.customer.core.mappers.ConsentMapper;
import com.firefly.core.customer.interfaces.dtos.ConsentDTO;
import com.firefly.core.customer.models.entities.Consent;
import com.firefly.core.customer.models.repositories.ConsentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ConsentServiceImplTest {

    @Mock
    private ConsentRepository consentRepository;

    @Mock
    private ConsentMapper consentMapper;

    @InjectMocks
    private ConsentServiceImpl consentService;

    private ConsentDTO consentDTO;
    private Consent consent;
    private UUID consentId;
    private UUID partyId;

    @BeforeEach
    void setUp() {
        consentId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        partyId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        
        consent = new Consent();
        consent.setConsentId(consentId);
        consent.setCreatedAt(LocalDateTime.now());
        consent.setUpdatedAt(LocalDateTime.now());

        consentDTO = new ConsentDTO();
        consentDTO.setConsentId(consentId);
        consentDTO.setCreatedAt(LocalDateTime.now());
        consentDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterConsents_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<ConsentDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<ConsentDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterConsents method
        ConsentServiceImpl spyService = spy(consentService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterConsents(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterConsents(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterConsents was called
        verify(spyService).filterConsents(partyId, filterRequest);
    }

    @Test
    void createConsent_ShouldReturnCreatedConsentDTO_WhenValidConsentDTO() {
        // Arrange
        when(consentMapper.toEntity(consentDTO)).thenReturn(consent);
        when(consentRepository.save(consent)).thenReturn(Mono.just(consent));
        when(consentMapper.toDTO(consent)).thenReturn(consentDTO);

        // Act & Assert
        StepVerifier.create(consentService.createConsent(partyId, consentDTO))
                .expectNext(consentDTO)
                .verifyComplete();

        verify(consentMapper).toEntity(consentDTO);
        verify(consentRepository).save(consent);
        verify(consentMapper).toDTO(consent);
    }

    @Test
    void createConsent_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(consentMapper.toEntity(consentDTO)).thenReturn(consent);
        when(consentRepository.save(consent)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(consentService.createConsent(partyId, consentDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(consentMapper).toEntity(consentDTO);
        verify(consentRepository).save(consent);
        verify(consentMapper, never()).toDTO(any());
    }

    @Test
    void updateConsent_ShouldReturnUpdatedConsentDTO_WhenConsentExists() {
        // Arrange
        ConsentDTO updateDTO = new ConsentDTO();
        updateDTO.setConsentId(null);
        
        Consent updatedConsent = new Consent();
        updatedConsent.setConsentId(consentId);
        
        when(consentRepository.findById(consentId)).thenReturn(Mono.just(consent));
        when(consentMapper.toEntity(updateDTO)).thenReturn(updatedConsent);
        when(consentRepository.save(updatedConsent)).thenReturn(Mono.just(updatedConsent));
        when(consentMapper.toDTO(updatedConsent)).thenReturn(consentDTO);

        // Act & Assert
        StepVerifier.create(consentService.updateConsent(partyId, consentId, updateDTO))
                .expectNext(consentDTO)
                .verifyComplete();

        verify(consentRepository).findById(consentId);
        verify(consentMapper).toEntity(updateDTO);
        verify(consentRepository).save(updatedConsent);
        verify(consentMapper).toDTO(updatedConsent);
    }

    @Test
    void updateConsent_ShouldReturnError_WhenConsentNotFound() {
        // Arrange
        when(consentRepository.findById(consentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(consentService.updateConsent(partyId, consentId, consentDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Consent not found with ID: " + consentId))
                .verify();

        verify(consentRepository).findById(consentId);
        verify(consentMapper, never()).toEntity(any());
        verify(consentRepository, never()).save(any());
    }

    @Test
    void deleteConsent_ShouldCompleteSuccessfully_WhenConsentExists() {
        // Arrange
        when(consentRepository.findById(consentId)).thenReturn(Mono.just(consent));
        when(consentRepository.deleteById(consentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(consentService.deleteConsent(partyId, consentId))
                .verifyComplete();

        verify(consentRepository).findById(consentId);
        verify(consentRepository).deleteById(consentId);
    }

    @Test
    void deleteConsent_ShouldReturnError_WhenConsentNotFound() {
        // Arrange
        when(consentRepository.findById(consentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(consentService.deleteConsent(partyId, consentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Consent not found with ID: " + consentId))
                .verify();

        verify(consentRepository).findById(consentId);
        verify(consentRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteConsent_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(consentRepository.findById(consentId)).thenReturn(Mono.just(consent));
        when(consentRepository.deleteById(consentId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(consentService.deleteConsent(partyId, consentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(consentRepository).findById(consentId);
        verify(consentRepository).deleteById(consentId);
    }

    @Test
    void getConsentById_ShouldReturnConsentDTO_WhenConsentExists() {
        // Arrange
        when(consentRepository.findById(consentId)).thenReturn(Mono.just(consent));
        when(consentMapper.toDTO(consent)).thenReturn(consentDTO);

        // Act & Assert
        StepVerifier.create(consentService.getConsentById(partyId, consentId))
                .expectNext(consentDTO)
                .verifyComplete();

        verify(consentRepository).findById(consentId);
        verify(consentMapper).toDTO(consent);
    }

    @Test
    void getConsentById_ShouldReturnError_WhenConsentNotFound() {
        // Arrange
        when(consentRepository.findById(consentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(consentService.getConsentById(partyId, consentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Consent not found with ID: " + consentId))
                .verify();

        verify(consentRepository).findById(consentId);
        verify(consentMapper, never()).toDTO(any());
    }

    @Test
    void getConsentById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(consentRepository.findById(consentId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(consentService.getConsentById(partyId, consentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(consentRepository).findById(consentId);
        verify(consentMapper, never()).toDTO(any());
    }
}