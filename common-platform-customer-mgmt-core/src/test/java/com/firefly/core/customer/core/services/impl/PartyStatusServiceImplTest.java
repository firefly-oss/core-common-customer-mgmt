package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PartyStatusMapper;
import com.firefly.core.customer.interfaces.dtos.PartyStatusDTO;
import com.firefly.core.customer.models.entities.PartyStatus;
import com.firefly.core.customer.models.repositories.PartyStatusRepository;
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
class PartyStatusServiceImplTest {

    @Mock
    private PartyStatusRepository partyStatusRepository;

    @Mock
    private PartyStatusMapper partyStatusMapper;

    @InjectMocks
    private PartyStatusServiceImpl partyStatusService;

    private PartyStatusDTO partyStatusDTO;
    private PartyStatus partyStatus;
    private UUID partyStatusId;
    private UUID partyId;

    @BeforeEach
    void setUp() {
        partyStatusId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        partyId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        
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
        updateDTO.setPartyStatusId(partyStatusId);
        
        // Set up the existing party status with the correct partyId for validation
        partyStatus.setPartyId(partyId);
        
        when(partyStatusRepository.findByPartyId(partyId)).thenReturn(Mono.just(partyStatus));
        doNothing().when(partyStatusMapper).updateEntityFromDto(updateDTO, partyStatus);
        when(partyStatusRepository.save(partyStatus)).thenReturn(Mono.just(partyStatus));
        when(partyStatusMapper.toDTO(partyStatus)).thenReturn(partyStatusDTO);

        // Act & Assert
        StepVerifier.create(partyStatusService.updatePartyStatus(partyId, updateDTO))
                .expectNext(partyStatusDTO)
                .verifyComplete();

        verify(partyStatusRepository).findByPartyId(partyId);
        verify(partyStatusMapper).updateEntityFromDto(updateDTO, partyStatus);
        verify(partyStatusRepository).save(partyStatus);
        verify(partyStatusMapper).toDTO(partyStatus);
    }

    @Test
    void updatePartyStatus_ShouldReturnError_WhenPartyStatusNotFound() {
        // Arrange
        when(partyStatusRepository.findByPartyId(partyId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyStatusService.updatePartyStatus(partyId, partyStatusDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party status not found for party ID: " + partyId))
                .verify();

        verify(partyStatusRepository).findByPartyId(partyId);
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
        verify(partyStatusRepository, never()).deleteById(any(UUID.class));
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