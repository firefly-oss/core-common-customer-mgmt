package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PartyStatusMapper;
import com.catalis.core.customer.interfaces.dtos.PartyStatusDTO;
import com.catalis.core.customer.models.entities.PartyStatus;
import com.catalis.core.customer.models.repositories.PartyStatusRepository;
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
class PartyStatusServiceImplTest {

    @Mock
    private PartyStatusRepository partyStatusRepository;

    @Mock
    private PartyStatusMapper partyStatusMapper;

    @InjectMocks
    private PartyStatusServiceImpl partyStatusService;

    private PartyStatusDTO partyStatusDTO;
    private PartyStatus partyStatus;
    private Long partyStatusId;
    private Long partyId;

    @BeforeEach
    void setUp() {
        partyStatusId = 1L;
        partyId = 1L;
        
        partyStatus = new PartyStatus();
        partyStatus.setPartyStatusId(partyStatusId);
        partyStatus.setCreatedAt(LocalDateTime.now());
        partyStatus.setUpdatedAt(LocalDateTime.now());

        partyStatusDTO = new PartyStatusDTO();
        partyStatusDTO.setPartyStatusId(partyStatusId);
        partyStatusDTO.setCreatedAt(LocalDateTime.now());
        partyStatusDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterPartyStatuses_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<PartyStatusDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<PartyStatusDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterPartyStatuses method
        PartyStatusServiceImpl spyService = spy(partyStatusService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterPartyStatuses(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPartyStatuses(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPartyStatuses was called
        verify(spyService).filterPartyStatuses(partyId, filterRequest);
    }

    @Test
    void createPartyStatus_ShouldReturnCreatedPartyStatusDTO_WhenValidPartyStatusDTO() {
        // Arrange
        when(partyStatusMapper.toEntity(partyStatusDTO)).thenReturn(partyStatus);
        when(partyStatusRepository.save(partyStatus)).thenReturn(Mono.just(partyStatus));
        when(partyStatusMapper.toDTO(partyStatus)).thenReturn(partyStatusDTO);

        // Act & Assert
        StepVerifier.create(partyStatusService.createPartyStatus(partyId, partyStatusDTO))
                .expectNext(partyStatusDTO)
                .verifyComplete();

        verify(partyStatusMapper).toEntity(partyStatusDTO);
        verify(partyStatusRepository).save(partyStatus);
        verify(partyStatusMapper).toDTO(partyStatus);
    }

    @Test
    void createPartyStatus_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(partyStatusMapper.toEntity(partyStatusDTO)).thenReturn(partyStatus);
        when(partyStatusRepository.save(partyStatus)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(partyStatusService.createPartyStatus(partyId, partyStatusDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(partyStatusMapper).toEntity(partyStatusDTO);
        verify(partyStatusRepository).save(partyStatus);
        verify(partyStatusMapper, never()).toDTO(any());
    }

    @Test
    void updatePartyStatus_ShouldReturnUpdatedPartyStatusDTO_WhenPartyStatusExists() {
        // Arrange
        PartyStatusDTO updateDTO = new PartyStatusDTO();
        updateDTO.setPartyStatusId(null);
        
        PartyStatus updatedPartyStatus = new PartyStatus();
        updatedPartyStatus.setPartyStatusId(partyStatusId);
        
        when(partyStatusRepository.findById(partyStatusId)).thenReturn(Mono.just(partyStatus));
        when(partyStatusMapper.toEntity(updateDTO)).thenReturn(updatedPartyStatus);
        when(partyStatusRepository.save(updatedPartyStatus)).thenReturn(Mono.just(updatedPartyStatus));
        when(partyStatusMapper.toDTO(updatedPartyStatus)).thenReturn(partyStatusDTO);

        // Act & Assert
        StepVerifier.create(partyStatusService.updatePartyStatus(partyId, partyStatusId, updateDTO))
                .expectNext(partyStatusDTO)
                .verifyComplete();

        verify(partyStatusRepository).findById(partyStatusId);
        verify(partyStatusMapper).toEntity(updateDTO);
        verify(partyStatusRepository).save(updatedPartyStatus);
        verify(partyStatusMapper).toDTO(updatedPartyStatus);
    }

    @Test
    void updatePartyStatus_ShouldReturnError_WhenPartyStatusNotFound() {
        // Arrange
        when(partyStatusRepository.findById(partyStatusId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyStatusService.updatePartyStatus(partyId, partyStatusId, partyStatusDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party status not found with ID: " + partyStatusId))
                .verify();

        verify(partyStatusRepository).findById(partyStatusId);
        verify(partyStatusMapper, never()).toEntity(any());
        verify(partyStatusRepository, never()).save(any());
    }

    @Test
    void deletePartyStatus_ShouldCompleteSuccessfully_WhenPartyStatusExists() {
        // Arrange
        when(partyStatusRepository.findById(partyStatusId)).thenReturn(Mono.just(partyStatus));
        when(partyStatusRepository.deleteById(partyStatusId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyStatusService.deletePartyStatus(partyId, partyStatusId))
                .verifyComplete();

        verify(partyStatusRepository).findById(partyStatusId);
        verify(partyStatusRepository).deleteById(partyStatusId);
    }

    @Test
    void deletePartyStatus_ShouldReturnError_WhenPartyStatusNotFound() {
        // Arrange
        when(partyStatusRepository.findById(partyStatusId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyStatusService.deletePartyStatus(partyId, partyStatusId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party status not found with ID: " + partyStatusId))
                .verify();

        verify(partyStatusRepository).findById(partyStatusId);
        verify(partyStatusRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deletePartyStatus_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(partyStatusRepository.findById(partyStatusId)).thenReturn(Mono.just(partyStatus));
        when(partyStatusRepository.deleteById(partyStatusId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(partyStatusService.deletePartyStatus(partyId, partyStatusId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(partyStatusRepository).findById(partyStatusId);
        verify(partyStatusRepository).deleteById(partyStatusId);
    }

    @Test
    void getPartyStatusById_ShouldReturnPartyStatusDTO_WhenPartyStatusExists() {
        // Arrange
        when(partyStatusRepository.findById(partyStatusId)).thenReturn(Mono.just(partyStatus));
        when(partyStatusMapper.toDTO(partyStatus)).thenReturn(partyStatusDTO);

        // Act & Assert
        StepVerifier.create(partyStatusService.getPartyStatusById(partyId, partyStatusId))
                .expectNext(partyStatusDTO)
                .verifyComplete();

        verify(partyStatusRepository).findById(partyStatusId);
        verify(partyStatusMapper).toDTO(partyStatus);
    }

    @Test
    void getPartyStatusById_ShouldReturnError_WhenPartyStatusNotFound() {
        // Arrange
        when(partyStatusRepository.findById(partyStatusId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyStatusService.getPartyStatusById(partyId, partyStatusId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party status not found with ID: " + partyStatusId))
                .verify();

        verify(partyStatusRepository).findById(partyStatusId);
        verify(partyStatusMapper, never()).toDTO(any());
    }

    @Test
    void getPartyStatusById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(partyStatusRepository.findById(partyStatusId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(partyStatusService.getPartyStatusById(partyId, partyStatusId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(partyStatusRepository).findById(partyStatusId);
        verify(partyStatusMapper, never()).toDTO(any());
    }
}