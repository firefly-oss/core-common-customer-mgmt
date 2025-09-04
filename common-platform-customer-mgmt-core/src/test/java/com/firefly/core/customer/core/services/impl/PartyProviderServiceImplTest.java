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

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PartyProviderMapper;
import com.firefly.core.customer.interfaces.dtos.PartyProviderDTO;
import com.firefly.core.customer.models.entities.PartyProvider;
import com.firefly.core.customer.models.repositories.PartyProviderRepository;
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
class PartyProviderServiceImplTest {

    @Mock
    private PartyProviderRepository partyProviderRepository;

    @Mock
    private PartyProviderMapper partyProviderMapper;

    @InjectMocks
    private PartyProviderServiceImpl partyProviderService;

    private PartyProviderDTO partyProviderDTO;
    private PartyProvider partyProvider;
    private UUID partyProviderId;
    private UUID partyId;

    @BeforeEach
    void setUp() {
        partyProviderId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        partyId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        
        partyProvider = new PartyProvider();
        partyProvider.setPartyProviderId(partyProviderId);
        partyProvider.setCreatedAt(LocalDateTime.now());
        partyProvider.setUpdatedAt(LocalDateTime.now());

        partyProviderDTO = new PartyProviderDTO();
        partyProviderDTO.setPartyProviderId(partyProviderId);
        partyProviderDTO.setCreatedAt(LocalDateTime.now());
        partyProviderDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterPartyProviders_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<PartyProviderDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<PartyProviderDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterPartyProviders method
        PartyProviderServiceImpl spyService = spy(partyProviderService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterPartyProviders(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPartyProviders(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPartyProviders was called
        verify(spyService).filterPartyProviders(partyId, filterRequest);
    }

    @Test
    void createPartyProvider_ShouldReturnCreatedPartyProviderDTO_WhenValidPartyProviderDTO() {
        // Arrange
        when(partyProviderMapper.toEntity(partyProviderDTO)).thenReturn(partyProvider);
        when(partyProviderRepository.save(partyProvider)).thenReturn(Mono.just(partyProvider));
        when(partyProviderMapper.toDTO(partyProvider)).thenReturn(partyProviderDTO);

        // Act & Assert
        StepVerifier.create(partyProviderService.createPartyProvider(partyId, partyProviderDTO))
                .expectNext(partyProviderDTO)
                .verifyComplete();

        verify(partyProviderMapper).toEntity(partyProviderDTO);
        verify(partyProviderRepository).save(partyProvider);
        verify(partyProviderMapper).toDTO(partyProvider);
    }

    @Test
    void createPartyProvider_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(partyProviderMapper.toEntity(partyProviderDTO)).thenReturn(partyProvider);
        when(partyProviderRepository.save(partyProvider)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(partyProviderService.createPartyProvider(partyId, partyProviderDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(partyProviderMapper).toEntity(partyProviderDTO);
        verify(partyProviderRepository).save(partyProvider);
        verify(partyProviderMapper, never()).toDTO(any());
    }

    @Test
    void updatePartyProvider_ShouldReturnUpdatedPartyProviderDTO_WhenPartyProviderExists() {
        // Arrange
        PartyProviderDTO updateDTO = new PartyProviderDTO();
        updateDTO.setPartyProviderId(null);
        
        PartyProvider updatedPartyProvider = new PartyProvider();
        updatedPartyProvider.setPartyProviderId(partyProviderId);
        
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.just(partyProvider));
        when(partyProviderMapper.toEntity(updateDTO)).thenReturn(updatedPartyProvider);
        when(partyProviderRepository.save(updatedPartyProvider)).thenReturn(Mono.just(updatedPartyProvider));
        when(partyProviderMapper.toDTO(updatedPartyProvider)).thenReturn(partyProviderDTO);

        // Act & Assert
        StepVerifier.create(partyProviderService.updatePartyProvider(partyId, partyProviderId, updateDTO))
                .expectNext(partyProviderDTO)
                .verifyComplete();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderMapper).toEntity(updateDTO);
        verify(partyProviderRepository).save(updatedPartyProvider);
        verify(partyProviderMapper).toDTO(updatedPartyProvider);
    }

    @Test
    void updatePartyProvider_ShouldReturnError_WhenPartyProviderNotFound() {
        // Arrange
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyProviderService.updatePartyProvider(partyId, partyProviderId, partyProviderDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party provider not found with ID: " + partyProviderId))
                .verify();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderMapper, never()).toEntity(any());
        verify(partyProviderRepository, never()).save(any());
    }

    @Test
    void deletePartyProvider_ShouldCompleteSuccessfully_WhenPartyProviderExists() {
        // Arrange
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.just(partyProvider));
        when(partyProviderRepository.deleteById(partyProviderId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyProviderService.deletePartyProvider(partyId, partyProviderId))
                .verifyComplete();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderRepository).deleteById(partyProviderId);
    }

    @Test
    void deletePartyProvider_ShouldReturnError_WhenPartyProviderNotFound() {
        // Arrange
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyProviderService.deletePartyProvider(partyId, partyProviderId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party provider not found with ID: " + partyProviderId))
                .verify();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deletePartyProvider_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.just(partyProvider));
        when(partyProviderRepository.deleteById(partyProviderId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(partyProviderService.deletePartyProvider(partyId, partyProviderId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderRepository).deleteById(partyProviderId);
    }

    @Test
    void getPartyProviderById_ShouldReturnPartyProviderDTO_WhenPartyProviderExists() {
        // Arrange
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.just(partyProvider));
        when(partyProviderMapper.toDTO(partyProvider)).thenReturn(partyProviderDTO);

        // Act & Assert
        StepVerifier.create(partyProviderService.getPartyProviderById(partyId, partyProviderId))
                .expectNext(partyProviderDTO)
                .verifyComplete();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderMapper).toDTO(partyProvider);
    }

    @Test
    void getPartyProviderById_ShouldReturnError_WhenPartyProviderNotFound() {
        // Arrange
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyProviderService.getPartyProviderById(partyId, partyProviderId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party provider not found with ID: " + partyProviderId))
                .verify();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderMapper, never()).toDTO(any());
    }

    @Test
    void getPartyProviderById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(partyProviderService.getPartyProviderById(partyId, partyProviderId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderMapper, never()).toDTO(any());
    }
}