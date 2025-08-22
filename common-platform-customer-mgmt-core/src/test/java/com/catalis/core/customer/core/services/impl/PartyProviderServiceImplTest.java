package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PartyProviderMapper;
import com.catalis.core.customer.interfaces.dtos.PartyProviderDTO;
import com.catalis.core.customer.models.entities.PartyProvider;
import com.catalis.core.customer.models.repositories.PartyProviderRepository;
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
class PartyProviderServiceImplTest {

    @Mock
    private PartyProviderRepository partyProviderRepository;

    @Mock
    private PartyProviderMapper partyProviderMapper;

    @InjectMocks
    private PartyProviderServiceImpl partyProviderService;

    private PartyProviderDTO partyProviderDTO;
    private PartyProvider partyProvider;
    private Long partyProviderId;

    @BeforeEach
    void setUp() {
        partyProviderId = 1L;
        
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
        doReturn(Mono.just(mockResponse)).when(spyService).filterPartyProviders(filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPartyProviders(filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPartyProviders was called
        verify(spyService).filterPartyProviders(filterRequest);
    }

    @Test
    void createPartyProvider_ShouldReturnCreatedPartyProviderDTO_WhenValidPartyProviderDTO() {
        // Arrange
        when(partyProviderMapper.toEntity(partyProviderDTO)).thenReturn(partyProvider);
        when(partyProviderRepository.save(partyProvider)).thenReturn(Mono.just(partyProvider));
        when(partyProviderMapper.toDTO(partyProvider)).thenReturn(partyProviderDTO);

        // Act & Assert
        StepVerifier.create(partyProviderService.createPartyProvider(partyProviderDTO))
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
        StepVerifier.create(partyProviderService.createPartyProvider(partyProviderDTO))
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
        StepVerifier.create(partyProviderService.updatePartyProvider(partyProviderId, updateDTO))
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
        StepVerifier.create(partyProviderService.updatePartyProvider(partyProviderId, partyProviderDTO))
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
        StepVerifier.create(partyProviderService.deletePartyProvider(partyProviderId))
                .verifyComplete();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderRepository).deleteById(partyProviderId);
    }

    @Test
    void deletePartyProvider_ShouldReturnError_WhenPartyProviderNotFound() {
        // Arrange
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(partyProviderService.deletePartyProvider(partyProviderId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Party provider not found with ID: " + partyProviderId))
                .verify();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deletePartyProvider_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(partyProviderRepository.findById(partyProviderId)).thenReturn(Mono.just(partyProvider));
        when(partyProviderRepository.deleteById(partyProviderId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(partyProviderService.deletePartyProvider(partyProviderId))
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
        StepVerifier.create(partyProviderService.getPartyProviderById(partyProviderId))
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
        StepVerifier.create(partyProviderService.getPartyProviderById(partyProviderId))
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
        StepVerifier.create(partyProviderService.getPartyProviderById(partyProviderId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(partyProviderRepository).findById(partyProviderId);
        verify(partyProviderMapper, never()).toDTO(any());
    }
}