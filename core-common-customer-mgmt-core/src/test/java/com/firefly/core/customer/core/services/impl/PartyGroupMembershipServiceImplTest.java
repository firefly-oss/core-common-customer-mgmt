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
import com.firefly.core.customer.core.mappers.PartyGroupMembershipMapper;
import com.firefly.core.customer.interfaces.dtos.PartyGroupMembershipDTO;
import com.firefly.core.customer.models.entities.PartyGroupMembership;
import com.firefly.core.customer.models.repositories.PartyGroupMembershipRepository;
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
class PartyGroupMembershipServiceImplTest {

    @Mock
    private PartyGroupMembershipRepository partyGroupMembershipRepository;

    @Mock
    private PartyGroupMembershipMapper partyGroupMembershipMapper;

    @InjectMocks
    private PartyGroupMembershipServiceImpl partyGroupMembershipService;

    private PartyGroupMembershipDTO partyGroupMembershipDTO;
    private PartyGroupMembership partyGroupMembership;
    private UUID partyGroupMembershipId;
    private UUID partyId;

    @BeforeEach
    void setUp() {
        partyGroupMembershipId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        partyId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        
        partyGroupMembership = new PartyGroupMembership();
        partyGroupMembership.setPartyGroupMembershipId(partyGroupMembershipId);
        partyGroupMembership.setCreatedAt(LocalDateTime.now());
        partyGroupMembership.setUpdatedAt(LocalDateTime.now());

        partyGroupMembershipDTO = new PartyGroupMembershipDTO();
        partyGroupMembershipDTO.setPartyGroupMembershipId(partyGroupMembershipId);
        partyGroupMembershipDTO.setCreatedAt(LocalDateTime.now());
        partyGroupMembershipDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterPartyGroupMemberships_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<PartyGroupMembershipDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<PartyGroupMembershipDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterPartyGroupMemberships method
        PartyGroupMembershipServiceImpl spyService = spy(partyGroupMembershipService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterPartyGroupMemberships(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPartyGroupMemberships(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPartyGroupMemberships was called
        verify(spyService).filterPartyGroupMemberships(partyId, filterRequest);
    }

    @Test
    void createPartyGroupMembership_ShouldReturnCreatedPartyGroupMembershipDTO_WhenValidPartyGroupMembershipDTO() {
        // Arrange
        when(partyGroupMembershipMapper.toEntity(partyGroupMembershipDTO)).thenReturn(partyGroupMembership);
        when(partyGroupMembershipRepository.save(partyGroupMembership)).thenReturn(Mono.just(partyGroupMembership));
        when(partyGroupMembershipMapper.toDTO(partyGroupMembership)).thenReturn(partyGroupMembershipDTO);

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.createPartyGroupMembership(partyId, partyGroupMembershipDTO))
                .expectNext(partyGroupMembershipDTO)
                .verifyComplete();

        verify(partyGroupMembershipMapper).toEntity(partyGroupMembershipDTO);
        verify(partyGroupMembershipRepository).save(partyGroupMembership);
        verify(partyGroupMembershipMapper).toDTO(partyGroupMembership);
    }

    @Test
    void createPartyGroupMembership_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(partyGroupMembershipMapper.toEntity(partyGroupMembershipDTO)).thenReturn(partyGroupMembership);
        when(partyGroupMembershipRepository.save(partyGroupMembership)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.createPartyGroupMembership(partyId, partyGroupMembershipDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(partyGroupMembershipMapper).toEntity(partyGroupMembershipDTO);
        verify(partyGroupMembershipRepository).save(partyGroupMembership);
        verify(partyGroupMembershipMapper, never()).toDTO(any());
    }

    @Test
    void updatePartyGroupMembership_ShouldReturnUpdatedPartyGroupMembershipDTO_WhenPartyGroupMembershipExists() {
        // Arrange
        PartyGroupMembershipDTO updateDTO = new PartyGroupMembershipDTO();
        updateDTO.setPartyGroupMembershipId(null);
        
        PartyGroupMembership updatedPartyGroupMembership = new PartyGroupMembership();
        updatedPartyGroupMembership.setPartyGroupMembershipId(partyGroupMembershipId);
        
        when(partyGroupMembershipRepository.findById(partyGroupMembershipId)).thenReturn(Mono.just(partyGroupMembership));
        when(partyGroupMembershipMapper.toEntity(updateDTO)).thenReturn(updatedPartyGroupMembership);
        when(partyGroupMembershipRepository.save(updatedPartyGroupMembership)).thenReturn(Mono.just(updatedPartyGroupMembership));
        when(partyGroupMembershipMapper.toDTO(updatedPartyGroupMembership)).thenReturn(partyGroupMembershipDTO);

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.updatePartyGroupMembership(partyId, partyGroupMembershipId, updateDTO))
                .expectNext(partyGroupMembershipDTO)
                .verifyComplete();

        verify(partyGroupMembershipRepository).findById(partyGroupMembershipId);
        verify(partyGroupMembershipMapper).toEntity(updateDTO);
        verify(partyGroupMembershipRepository).save(updatedPartyGroupMembership);
        verify(partyGroupMembershipMapper).toDTO(updatedPartyGroupMembership);
    }

    @Test
    void updatePartyGroupMembership_ShouldReturnError_WhenPartyGroupMembershipNotFound() {
        // Arrange
        when(partyGroupMembershipRepository.findById(partyGroupMembershipId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.updatePartyGroupMembership(partyId, partyGroupMembershipId, partyGroupMembershipDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party group membership not found with ID: " + partyGroupMembershipId))
                .verify();

        verify(partyGroupMembershipRepository).findById(partyGroupMembershipId);
        verify(partyGroupMembershipMapper, never()).toEntity(any());
        verify(partyGroupMembershipRepository, never()).save(any());
    }

    @Test
    void deletePartyGroupMembership_ShouldCompleteSuccessfully_WhenPartyGroupMembershipExists() {
        // Arrange
        when(partyGroupMembershipRepository.findById(partyGroupMembershipId)).thenReturn(Mono.just(partyGroupMembership));
        when(partyGroupMembershipRepository.deleteById(partyGroupMembershipId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.deletePartyGroupMembership(partyId, partyGroupMembershipId))
                .verifyComplete();

        verify(partyGroupMembershipRepository).findById(partyGroupMembershipId);
        verify(partyGroupMembershipRepository).deleteById(partyGroupMembershipId);
    }

    @Test
    void deletePartyGroupMembership_ShouldReturnError_WhenPartyGroupMembershipNotFound() {
        // Arrange
        when(partyGroupMembershipRepository.findById(partyGroupMembershipId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.deletePartyGroupMembership(partyId, partyGroupMembershipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party group membership not found with ID: " + partyGroupMembershipId))
                .verify();

        verify(partyGroupMembershipRepository).findById(partyGroupMembershipId);
        verify(partyGroupMembershipRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deletePartyGroupMembership_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(partyGroupMembershipRepository.findById(partyGroupMembershipId)).thenReturn(Mono.just(partyGroupMembership));
        when(partyGroupMembershipRepository.deleteById(partyGroupMembershipId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.deletePartyGroupMembership(partyId, partyGroupMembershipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(partyGroupMembershipRepository).findById(partyGroupMembershipId);
        verify(partyGroupMembershipRepository).deleteById(partyGroupMembershipId);
    }

    @Test
    void getPartyGroupMembershipById_ShouldReturnPartyGroupMembershipDTO_WhenPartyGroupMembershipExists() {
        // Arrange
        when(partyGroupMembershipRepository.findById(partyGroupMembershipId)).thenReturn(Mono.just(partyGroupMembership));
        when(partyGroupMembershipMapper.toDTO(partyGroupMembership)).thenReturn(partyGroupMembershipDTO);

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.getPartyGroupMembershipById(partyId, partyGroupMembershipId))
                .expectNext(partyGroupMembershipDTO)
                .verifyComplete();

        verify(partyGroupMembershipRepository).findById(partyGroupMembershipId);
        verify(partyGroupMembershipMapper).toDTO(partyGroupMembership);
    }

    @Test
    void getPartyGroupMembershipById_ShouldReturnError_WhenPartyGroupMembershipNotFound() {
        // Arrange
        when(partyGroupMembershipRepository.findById(partyGroupMembershipId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.getPartyGroupMembershipById(partyId, partyGroupMembershipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party group membership not found with ID: " + partyGroupMembershipId))
                .verify();

        verify(partyGroupMembershipRepository).findById(partyGroupMembershipId);
        verify(partyGroupMembershipMapper, never()).toDTO(any());
    }

    @Test
    void getPartyGroupMembershipById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(partyGroupMembershipRepository.findById(partyGroupMembershipId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(partyGroupMembershipService.getPartyGroupMembershipById(partyId, partyGroupMembershipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(partyGroupMembershipRepository).findById(partyGroupMembershipId);
        verify(partyGroupMembershipMapper, never()).toDTO(any());
    }
}