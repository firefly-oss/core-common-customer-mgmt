package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
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
    private Long consentId;
    private Long partyId;

    @BeforeEach
    void setUp() {
        consentId = 1L;
        partyId = 1L;
        
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
        verify(consentRepository, never()).deleteById(any(Long.class));
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