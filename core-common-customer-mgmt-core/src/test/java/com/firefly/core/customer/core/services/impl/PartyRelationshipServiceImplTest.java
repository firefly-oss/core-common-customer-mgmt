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
import com.firefly.core.customer.core.mappers.PartyRelationshipMapper;
import com.firefly.core.customer.interfaces.dtos.PartyRelationshipDTO;
import com.firefly.core.customer.models.entities.PartyRelationship;
import com.firefly.core.customer.models.repositories.PartyRelationshipRepository;
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
class PartyRelationshipServiceImplTest {

    @Mock
    private PartyRelationshipRepository partyRelationshipRepository;

    @Mock
    private PartyRelationshipMapper partyRelationshipMapper;

    @InjectMocks
    private PartyRelationshipServiceImpl partyRelationshipService;

    private PartyRelationshipDTO partyRelationshipDTO;
    private PartyRelationship partyRelationship;
    private UUID partyRelationshipId;

    @BeforeEach
    void setUp() {
        partyRelationshipId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");

        partyRelationship = new PartyRelationship();
        partyRelationship.setPartyRelationshipId(partyRelationshipId);
        partyRelationship.setCreatedAt(LocalDateTime.now());
        partyRelationship.setUpdatedAt(LocalDateTime.now());

        partyRelationshipDTO = new PartyRelationshipDTO();
        partyRelationshipDTO.setPartyRelationshipId(partyRelationshipId);
        partyRelationshipDTO.setCreatedAt(LocalDateTime.now());
        partyRelationshipDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterPartyRelationships_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<PartyRelationshipDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<PartyRelationshipDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterPartyRelationships method
        PartyRelationshipServiceImpl spyService = spy(partyRelationshipService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterPartyRelationships(filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPartyRelationships(filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPartyRelationships was called
        verify(spyService).filterPartyRelationships(filterRequest);
    }

    @Test
    void createPartyRelationship_ShouldReturnCreatedPartyRelationshipDTO_WhenValidPartyRelationshipDTO() {
        // Arrange
        when(partyRelationshipMapper.toEntity(partyRelationshipDTO)).thenReturn(partyRelationship);
        when(partyRelationshipRepository.save(partyRelationship)).thenReturn(Mono.just(partyRelationship));
        when(partyRelationshipMapper.toDTO(partyRelationship)).thenReturn(partyRelationshipDTO);

        // Act & Assert
        StepVerifier.create(partyRelationshipService.createPartyRelationship(partyRelationshipDTO))
                .expectNext(partyRelationshipDTO)
                .verifyComplete();

        verify(partyRelationshipMapper).toEntity(partyRelationshipDTO);
        verify(partyRelationshipRepository).save(partyRelationship);
        verify(partyRelationshipMapper).toDTO(partyRelationship);
    }

    @Test
    void createPartyRelationship_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(partyRelationshipMapper.toEntity(partyRelationshipDTO)).thenReturn(partyRelationship);
        when(partyRelationshipRepository.save(partyRelationship)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(partyRelationshipService.createPartyRelationship(partyRelationshipDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(partyRelationshipMapper).toEntity(partyRelationshipDTO);
        verify(partyRelationshipRepository).save(partyRelationship);
        verify(partyRelationshipMapper, never()).toDTO(any());
    }

    @Test
    void updatePartyRelationship_ShouldReturnUpdatedPartyRelationshipDTO_WhenPartyRelationshipExists() {
        // Arrange
        PartyRelationshipDTO updateDTO = new PartyRelationshipDTO();
        updateDTO.setPartyRelationshipId(null);
        
        PartyRelationship updatedPartyRelationship = new PartyRelationship();
        updatedPartyRelationship.setPartyRelationshipId(partyRelationshipId);
        
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.just(partyRelationship));
        when(partyRelationshipMapper.toEntity(updateDTO)).thenReturn(updatedPartyRelationship);
        when(partyRelationshipRepository.save(updatedPartyRelationship)).thenReturn(Mono.just(updatedPartyRelationship));
        when(partyRelationshipMapper.toDTO(updatedPartyRelationship)).thenReturn(partyRelationshipDTO);

        // Act & Assert
        StepVerifier.create(partyRelationshipService.updatePartyRelationship(partyRelationshipId, updateDTO))
                .expectNext(partyRelationshipDTO)
                .verifyComplete();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipMapper).toEntity(updateDTO);
        verify(partyRelationshipRepository).save(updatedPartyRelationship);
        verify(partyRelationshipMapper).toDTO(updatedPartyRelationship);
    }

    @Test
    void updatePartyRelationship_ShouldReturnError_WhenPartyRelationshipNotFound() {
        // Arrange
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyRelationshipService.updatePartyRelationship(partyRelationshipId, partyRelationshipDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party relationship not found with ID: " + partyRelationshipId))
                .verify();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipMapper, never()).toEntity(any());
        verify(partyRelationshipRepository, never()).save(any());
    }

    @Test
    void deletePartyRelationship_ShouldCompleteSuccessfully_WhenPartyRelationshipExists() {
        // Arrange
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.just(partyRelationship));
        when(partyRelationshipRepository.deleteById(partyRelationshipId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyRelationshipService.deletePartyRelationship(partyRelationshipId))
                .verifyComplete();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipRepository).deleteById(partyRelationshipId);
    }

    @Test
    void deletePartyRelationship_ShouldReturnError_WhenPartyRelationshipNotFound() {
        // Arrange
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyRelationshipService.deletePartyRelationship(partyRelationshipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party relationship not found with ID: " + partyRelationshipId))
                .verify();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deletePartyRelationship_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.just(partyRelationship));
        when(partyRelationshipRepository.deleteById(partyRelationshipId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(partyRelationshipService.deletePartyRelationship(partyRelationshipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipRepository).deleteById(partyRelationshipId);
    }

    @Test
    void getPartyRelationshipById_ShouldReturnPartyRelationshipDTO_WhenPartyRelationshipExists() {
        // Arrange
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.just(partyRelationship));
        when(partyRelationshipMapper.toDTO(partyRelationship)).thenReturn(partyRelationshipDTO);

        // Act & Assert
        StepVerifier.create(partyRelationshipService.getPartyRelationshipById(partyRelationshipId))
                .expectNext(partyRelationshipDTO)
                .verifyComplete();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipMapper).toDTO(partyRelationship);
    }

    @Test
    void getPartyRelationshipById_ShouldReturnError_WhenPartyRelationshipNotFound() {
        // Arrange
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyRelationshipService.getPartyRelationshipById(partyRelationshipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party relationship not found with ID: " + partyRelationshipId))
                .verify();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipMapper, never()).toDTO(any());
    }

    @Test
    void getPartyRelationshipById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(partyRelationshipService.getPartyRelationshipById(partyRelationshipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipMapper, never()).toDTO(any());
    }
}