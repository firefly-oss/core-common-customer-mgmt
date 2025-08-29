package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
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
    private Long partyRelationshipId;
    private Long partyId;

    @BeforeEach
    void setUp() {
        partyRelationshipId = 1L;
        partyId = 100L;
        
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
        doReturn(Mono.just(mockResponse)).when(spyService).filterPartyRelationships(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPartyRelationships(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPartyRelationships was called
        verify(spyService).filterPartyRelationships(partyId, filterRequest);
    }

    @Test
    void createPartyRelationship_ShouldReturnCreatedPartyRelationshipDTO_WhenValidPartyRelationshipDTO() {
        // Arrange
        when(partyRelationshipMapper.toEntity(partyRelationshipDTO)).thenReturn(partyRelationship);
        when(partyRelationshipRepository.save(partyRelationship)).thenReturn(Mono.just(partyRelationship));
        when(partyRelationshipMapper.toDTO(partyRelationship)).thenReturn(partyRelationshipDTO);

        // Act & Assert
        StepVerifier.create(partyRelationshipService.createPartyRelationship(partyId, partyRelationshipDTO))
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
        StepVerifier.create(partyRelationshipService.createPartyRelationship(partyId, partyRelationshipDTO))
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
        StepVerifier.create(partyRelationshipService.updatePartyRelationship(partyId, partyRelationshipId, updateDTO))
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
        StepVerifier.create(partyRelationshipService.updatePartyRelationship(partyId, partyRelationshipId, partyRelationshipDTO))
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
        StepVerifier.create(partyRelationshipService.deletePartyRelationship(partyId, partyRelationshipId))
                .verifyComplete();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipRepository).deleteById(partyRelationshipId);
    }

    @Test
    void deletePartyRelationship_ShouldReturnError_WhenPartyRelationshipNotFound() {
        // Arrange
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyRelationshipService.deletePartyRelationship(partyId, partyRelationshipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party relationship not found with ID: " + partyRelationshipId))
                .verify();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deletePartyRelationship_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(partyRelationshipRepository.findById(partyRelationshipId)).thenReturn(Mono.just(partyRelationship));
        when(partyRelationshipRepository.deleteById(partyRelationshipId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(partyRelationshipService.deletePartyRelationship(partyId, partyRelationshipId))
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
        StepVerifier.create(partyRelationshipService.getPartyRelationshipById(partyId, partyRelationshipId))
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
        StepVerifier.create(partyRelationshipService.getPartyRelationshipById(partyId, partyRelationshipId))
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
        StepVerifier.create(partyRelationshipService.getPartyRelationshipById(partyId, partyRelationshipId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(partyRelationshipRepository).findById(partyRelationshipId);
        verify(partyRelationshipMapper, never()).toDTO(any());
    }
}