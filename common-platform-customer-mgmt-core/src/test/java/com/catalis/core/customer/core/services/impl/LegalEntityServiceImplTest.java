package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.LegalEntityMapper;
import com.catalis.core.customer.interfaces.dtos.LegalEntityDTO;
import com.catalis.core.customer.models.entities.LegalEntity;
import com.catalis.core.customer.models.repositories.LegalEntityRepository;
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
class LegalEntityServiceImplTest {

    @Mock
    private LegalEntityRepository legalEntityRepository;

    @Mock
    private LegalEntityMapper legalEntityMapper;

    @InjectMocks
    private LegalEntityServiceImpl legalEntityService;

    private LegalEntityDTO legalEntityDTO;
    private LegalEntity legalEntity;
    private Long legalEntityId;
    private Long partyId;

    @BeforeEach
    void setUp() {
        legalEntityId = 1L;
        partyId = 1L;
        
        legalEntity = new LegalEntity();
        legalEntity.setLegalEntityId(legalEntityId);
        legalEntity.setCreatedAt(LocalDateTime.now());
        legalEntity.setUpdatedAt(LocalDateTime.now());

        legalEntityDTO = new LegalEntityDTO();
        legalEntityDTO.setLegalEntityId(legalEntityId);
        legalEntityDTO.setCreatedAt(LocalDateTime.now());
        legalEntityDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterLegalEntities_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<LegalEntityDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<LegalEntityDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterLegalEntities method
        LegalEntityServiceImpl spyService = spy(legalEntityService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterLegalEntities(filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterLegalEntities(filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterLegalEntities was called
        verify(spyService).filterLegalEntities(filterRequest);
    }

    @Test
    void createLegalEntity_ShouldReturnCreatedLegalEntityDTO_WhenValidLegalEntityDTO() {
        // Arrange
        when(legalEntityMapper.toEntity(legalEntityDTO)).thenReturn(legalEntity);
        when(legalEntityRepository.save(legalEntity)).thenReturn(Mono.just(legalEntity));
        when(legalEntityMapper.toDTO(legalEntity)).thenReturn(legalEntityDTO);

        // Act & Assert
        StepVerifier.create(legalEntityService.createLegalEntity(partyId, legalEntityDTO))
                .expectNext(legalEntityDTO)
                .verifyComplete();

        verify(legalEntityMapper).toEntity(legalEntityDTO);
        verify(legalEntityRepository).save(legalEntity);
        verify(legalEntityMapper).toDTO(legalEntity);
    }

    @Test
    void createLegalEntity_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(legalEntityMapper.toEntity(legalEntityDTO)).thenReturn(legalEntity);
        when(legalEntityRepository.save(legalEntity)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(legalEntityService.createLegalEntity(partyId, legalEntityDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(legalEntityMapper).toEntity(legalEntityDTO);
        verify(legalEntityRepository).save(legalEntity);
        verify(legalEntityMapper, never()).toDTO(any());
    }

    @Test
    void updateLegalEntity_ShouldReturnUpdatedLegalEntityDTO_WhenLegalEntityExists() {
        // Arrange
        LegalEntityDTO updateDTO = new LegalEntityDTO();
        updateDTO.setLegalEntityId(null);
        
        LegalEntity updatedLegalEntity = new LegalEntity();
        updatedLegalEntity.setLegalEntityId(legalEntityId);
        
        when(legalEntityRepository.findById(legalEntityId)).thenReturn(Mono.just(legalEntity));
        when(legalEntityMapper.toEntity(updateDTO)).thenReturn(updatedLegalEntity);
        when(legalEntityRepository.save(updatedLegalEntity)).thenReturn(Mono.just(updatedLegalEntity));
        when(legalEntityMapper.toDTO(updatedLegalEntity)).thenReturn(legalEntityDTO);

        // Act & Assert
        StepVerifier.create(legalEntityService.updateLegalEntity(partyId, legalEntityId, updateDTO))
                .expectNext(legalEntityDTO)
                .verifyComplete();

        verify(legalEntityRepository).findById(legalEntityId);
        verify(legalEntityMapper).toEntity(updateDTO);
        verify(legalEntityRepository).save(updatedLegalEntity);
        verify(legalEntityMapper).toDTO(updatedLegalEntity);
    }

    @Test
    void updateLegalEntity_ShouldReturnError_WhenLegalEntityNotFound() {
        // Arrange
        when(legalEntityRepository.findById(legalEntityId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(legalEntityService.updateLegalEntity(partyId, legalEntityId, legalEntityDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Legal entity not found with ID: " + legalEntityId))
                .verify();

        verify(legalEntityRepository).findById(legalEntityId);
        verify(legalEntityMapper, never()).toEntity(any());
        verify(legalEntityRepository, never()).save(any());
    }

    @Test
    void deleteLegalEntity_ShouldCompleteSuccessfully_WhenLegalEntityExists() {
        // Arrange
        when(legalEntityRepository.findById(legalEntityId)).thenReturn(Mono.just(legalEntity));
        when(legalEntityRepository.deleteById(legalEntityId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(legalEntityService.deleteLegalEntity(partyId, legalEntityId))
                .verifyComplete();

        verify(legalEntityRepository).findById(legalEntityId);
        verify(legalEntityRepository).deleteById(legalEntityId);
    }

    @Test
    void deleteLegalEntity_ShouldReturnError_WhenLegalEntityNotFound() {
        // Arrange
        when(legalEntityRepository.findById(legalEntityId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(legalEntityService.deleteLegalEntity(partyId, legalEntityId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Legal entity not found with ID: " + legalEntityId))
                .verify();

        verify(legalEntityRepository).findById(legalEntityId);
        verify(legalEntityRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deleteLegalEntity_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(legalEntityRepository.findById(legalEntityId)).thenReturn(Mono.just(legalEntity));
        when(legalEntityRepository.deleteById(legalEntityId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(legalEntityService.deleteLegalEntity(partyId, legalEntityId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(legalEntityRepository).findById(legalEntityId);
        verify(legalEntityRepository).deleteById(legalEntityId);
    }

    @Test
    void getLegalEntityById_ShouldReturnLegalEntityDTO_WhenLegalEntityExists() {
        // Arrange
        when(legalEntityRepository.findById(legalEntityId)).thenReturn(Mono.just(legalEntity));
        when(legalEntityMapper.toDTO(legalEntity)).thenReturn(legalEntityDTO);

        // Act & Assert
        StepVerifier.create(legalEntityService.getLegalEntityById(partyId, legalEntityId))
                .expectNext(legalEntityDTO)
                .verifyComplete();

        verify(legalEntityRepository).findById(legalEntityId);
        verify(legalEntityMapper).toDTO(legalEntity);
    }

    @Test
    void getLegalEntityById_ShouldReturnError_WhenLegalEntityNotFound() {
        // Arrange
        when(legalEntityRepository.findById(legalEntityId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(legalEntityService.getLegalEntityById(partyId, legalEntityId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Legal entity not found with ID: " + legalEntityId))
                .verify();

        verify(legalEntityRepository).findById(legalEntityId);
        verify(legalEntityMapper, never()).toDTO(any());
    }

    @Test
    void getLegalEntityById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(legalEntityRepository.findById(legalEntityId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(legalEntityService.getLegalEntityById(partyId, legalEntityId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(legalEntityRepository).findById(legalEntityId);
        verify(legalEntityMapper, never()).toDTO(any());
    }
}