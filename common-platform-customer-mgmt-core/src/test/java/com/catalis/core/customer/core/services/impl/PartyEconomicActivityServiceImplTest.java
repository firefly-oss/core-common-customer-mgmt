package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PartyEconomicActivityMapper;
import com.catalis.core.customer.interfaces.dtos.PartyEconomicActivityDTO;
import com.catalis.core.customer.models.entities.PartyEconomicActivity;
import com.catalis.core.customer.models.repositories.PartyEconomicActivityRepository;
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
class PartyEconomicActivityServiceImplTest {

    @Mock
    private PartyEconomicActivityRepository partyEconomicActivityRepository;

    @Mock
    private PartyEconomicActivityMapper partyEconomicActivityMapper;

    @InjectMocks
    private PartyEconomicActivityServiceImpl partyEconomicActivityService;

    private PartyEconomicActivityDTO partyEconomicActivityDTO;
    private PartyEconomicActivity partyEconomicActivity;
    private Long partyEconomicActivityId;
    private Long partyId;

    @BeforeEach
    void setUp() {
        partyEconomicActivityId = 1L;
        partyId = 1L;
        
        partyEconomicActivity = new PartyEconomicActivity();
        partyEconomicActivity.setPartyEconomicActivityId(partyEconomicActivityId);
        partyEconomicActivity.setCreatedAt(LocalDateTime.now());
        partyEconomicActivity.setUpdatedAt(LocalDateTime.now());

        partyEconomicActivityDTO = new PartyEconomicActivityDTO();
        partyEconomicActivityDTO.setPartyEconomicActivityId(partyEconomicActivityId);
        partyEconomicActivityDTO.setCreatedAt(LocalDateTime.now());
        partyEconomicActivityDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterPartyEconomicActivities_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<PartyEconomicActivityDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<PartyEconomicActivityDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterPartyEconomicActivities method
        PartyEconomicActivityServiceImpl spyService = spy(partyEconomicActivityService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterPartyEconomicActivities(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPartyEconomicActivities(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPartyEconomicActivities was called
        verify(spyService).filterPartyEconomicActivities(partyId, filterRequest);
    }

    @Test
    void createPartyEconomicActivity_ShouldReturnCreatedPartyEconomicActivityDTO_WhenValidPartyEconomicActivityDTO() {
        // Arrange
        when(partyEconomicActivityMapper.toEntity(partyEconomicActivityDTO)).thenReturn(partyEconomicActivity);
        when(partyEconomicActivityRepository.save(partyEconomicActivity)).thenReturn(Mono.just(partyEconomicActivity));
        when(partyEconomicActivityMapper.toDTO(partyEconomicActivity)).thenReturn(partyEconomicActivityDTO);

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.createPartyEconomicActivity(partyId, partyEconomicActivityDTO))
                .expectNext(partyEconomicActivityDTO)
                .verifyComplete();

        verify(partyEconomicActivityMapper).toEntity(partyEconomicActivityDTO);
        verify(partyEconomicActivityRepository).save(partyEconomicActivity);
        verify(partyEconomicActivityMapper).toDTO(partyEconomicActivity);
    }

    @Test
    void createPartyEconomicActivity_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(partyEconomicActivityMapper.toEntity(partyEconomicActivityDTO)).thenReturn(partyEconomicActivity);
        when(partyEconomicActivityRepository.save(partyEconomicActivity)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.createPartyEconomicActivity(partyId, partyEconomicActivityDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(partyEconomicActivityMapper).toEntity(partyEconomicActivityDTO);
        verify(partyEconomicActivityRepository).save(partyEconomicActivity);
        verify(partyEconomicActivityMapper, never()).toDTO(any());
    }

    @Test
    void updatePartyEconomicActivity_ShouldReturnUpdatedPartyEconomicActivityDTO_WhenPartyEconomicActivityExists() {
        // Arrange
        PartyEconomicActivityDTO updateDTO = new PartyEconomicActivityDTO();
        updateDTO.setPartyEconomicActivityId(null);
        
        PartyEconomicActivity updatedPartyEconomicActivity = new PartyEconomicActivity();
        updatedPartyEconomicActivity.setPartyEconomicActivityId(partyEconomicActivityId);
        
        when(partyEconomicActivityRepository.findById(partyEconomicActivityId)).thenReturn(Mono.just(partyEconomicActivity));
        when(partyEconomicActivityMapper.toEntity(updateDTO)).thenReturn(updatedPartyEconomicActivity);
        when(partyEconomicActivityRepository.save(updatedPartyEconomicActivity)).thenReturn(Mono.just(updatedPartyEconomicActivity));
        when(partyEconomicActivityMapper.toDTO(updatedPartyEconomicActivity)).thenReturn(partyEconomicActivityDTO);

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.updatePartyEconomicActivity(partyId, partyEconomicActivityId, updateDTO))
                .expectNext(partyEconomicActivityDTO)
                .verifyComplete();

        verify(partyEconomicActivityRepository).findById(partyEconomicActivityId);
        verify(partyEconomicActivityMapper).toEntity(updateDTO);
        verify(partyEconomicActivityRepository).save(updatedPartyEconomicActivity);
        verify(partyEconomicActivityMapper).toDTO(updatedPartyEconomicActivity);
    }

    @Test
    void updatePartyEconomicActivity_ShouldReturnError_WhenPartyEconomicActivityNotFound() {
        // Arrange
        when(partyEconomicActivityRepository.findById(partyEconomicActivityId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.updatePartyEconomicActivity(partyId, partyEconomicActivityId, partyEconomicActivityDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party economic activity not found with ID: " + partyEconomicActivityId))
                .verify();

        verify(partyEconomicActivityRepository).findById(partyEconomicActivityId);
        verify(partyEconomicActivityMapper, never()).toEntity(any());
        verify(partyEconomicActivityRepository, never()).save(any());
    }

    @Test
    void deletePartyEconomicActivity_ShouldCompleteSuccessfully_WhenPartyEconomicActivityExists() {
        // Arrange
        when(partyEconomicActivityRepository.findById(partyEconomicActivityId)).thenReturn(Mono.just(partyEconomicActivity));
        when(partyEconomicActivityRepository.deleteById(partyEconomicActivityId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.deletePartyEconomicActivity(partyId, partyEconomicActivityId))
                .verifyComplete();

        verify(partyEconomicActivityRepository).findById(partyEconomicActivityId);
        verify(partyEconomicActivityRepository).deleteById(partyEconomicActivityId);
    }

    @Test
    void deletePartyEconomicActivity_ShouldReturnError_WhenPartyEconomicActivityNotFound() {
        // Arrange
        when(partyEconomicActivityRepository.findById(partyEconomicActivityId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.deletePartyEconomicActivity(partyId, partyEconomicActivityId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party economic activity not found with ID: " + partyEconomicActivityId))
                .verify();

        verify(partyEconomicActivityRepository).findById(partyEconomicActivityId);
        verify(partyEconomicActivityRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deletePartyEconomicActivity_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(partyEconomicActivityRepository.findById(partyEconomicActivityId)).thenReturn(Mono.just(partyEconomicActivity));
        when(partyEconomicActivityRepository.deleteById(partyEconomicActivityId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.deletePartyEconomicActivity(partyId, partyEconomicActivityId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(partyEconomicActivityRepository).findById(partyEconomicActivityId);
        verify(partyEconomicActivityRepository).deleteById(partyEconomicActivityId);
    }

    @Test
    void getPartyEconomicActivityById_ShouldReturnPartyEconomicActivityDTO_WhenPartyEconomicActivityExists() {
        // Arrange
        when(partyEconomicActivityRepository.findById(partyEconomicActivityId)).thenReturn(Mono.just(partyEconomicActivity));
        when(partyEconomicActivityMapper.toDTO(partyEconomicActivity)).thenReturn(partyEconomicActivityDTO);

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.getPartyEconomicActivityById(partyId, partyEconomicActivityId))
                .expectNext(partyEconomicActivityDTO)
                .verifyComplete();

        verify(partyEconomicActivityRepository).findById(partyEconomicActivityId);
        verify(partyEconomicActivityMapper).toDTO(partyEconomicActivity);
    }

    @Test
    void getPartyEconomicActivityById_ShouldReturnError_WhenPartyEconomicActivityNotFound() {
        // Arrange
        when(partyEconomicActivityRepository.findById(partyEconomicActivityId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.getPartyEconomicActivityById(partyId, partyEconomicActivityId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party economic activity not found with ID: " + partyEconomicActivityId))
                .verify();

        verify(partyEconomicActivityRepository).findById(partyEconomicActivityId);
        verify(partyEconomicActivityMapper, never()).toDTO(any());
    }

    @Test
    void getPartyEconomicActivityById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(partyEconomicActivityRepository.findById(partyEconomicActivityId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(partyEconomicActivityService.getPartyEconomicActivityById(partyId, partyEconomicActivityId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(partyEconomicActivityRepository).findById(partyEconomicActivityId);
        verify(partyEconomicActivityMapper, never()).toDTO(any());
    }
}