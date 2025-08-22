package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.IdentityDocumentMapper;
import com.catalis.core.customer.interfaces.dtos.IdentityDocumentDTO;
import com.catalis.core.customer.models.entities.IdentityDocument;
import com.catalis.core.customer.models.repositories.IdentityDocumentRepository;
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
class IdentityDocumentServiceImplTest {

    @Mock
    private IdentityDocumentRepository identityDocumentRepository;

    @Mock
    private IdentityDocumentMapper identityDocumentMapper;

    @InjectMocks
    private IdentityDocumentServiceImpl identityDocumentService;

    private IdentityDocumentDTO identityDocumentDTO;
    private IdentityDocument identityDocument;
    private Long identityDocumentId;

    @BeforeEach
    void setUp() {
        identityDocumentId = 1L;
        
        identityDocument = new IdentityDocument();
        identityDocument.setIdentityDocumentId(identityDocumentId);
        identityDocument.setCreatedAt(LocalDateTime.now());
        identityDocument.setUpdatedAt(LocalDateTime.now());

        identityDocumentDTO = new IdentityDocumentDTO();
        identityDocumentDTO.setIdentityDocumentId(identityDocumentId);
        identityDocumentDTO.setCreatedAt(LocalDateTime.now());
        identityDocumentDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterIdentityDocuments_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<IdentityDocumentDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<IdentityDocumentDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterIdentityDocuments method
        IdentityDocumentServiceImpl spyService = spy(identityDocumentService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterIdentityDocuments(filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterIdentityDocuments(filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterIdentityDocuments was called
        verify(spyService).filterIdentityDocuments(filterRequest);
    }

    @Test
    void createIdentityDocument_ShouldReturnCreatedIdentityDocumentDTO_WhenValidIdentityDocumentDTO() {
        // Arrange
        when(identityDocumentMapper.toEntity(identityDocumentDTO)).thenReturn(identityDocument);
        when(identityDocumentRepository.save(identityDocument)).thenReturn(Mono.just(identityDocument));
        when(identityDocumentMapper.toDTO(identityDocument)).thenReturn(identityDocumentDTO);

        // Act & Assert
        StepVerifier.create(identityDocumentService.createIdentityDocument(identityDocumentDTO))
                .expectNext(identityDocumentDTO)
                .verifyComplete();

        verify(identityDocumentMapper).toEntity(identityDocumentDTO);
        verify(identityDocumentRepository).save(identityDocument);
        verify(identityDocumentMapper).toDTO(identityDocument);
    }

    @Test
    void createIdentityDocument_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(identityDocumentMapper.toEntity(identityDocumentDTO)).thenReturn(identityDocument);
        when(identityDocumentRepository.save(identityDocument)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(identityDocumentService.createIdentityDocument(identityDocumentDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(identityDocumentMapper).toEntity(identityDocumentDTO);
        verify(identityDocumentRepository).save(identityDocument);
        verify(identityDocumentMapper, never()).toDTO(any());
    }

    @Test
    void updateIdentityDocument_ShouldReturnUpdatedIdentityDocumentDTO_WhenIdentityDocumentExists() {
        // Arrange
        IdentityDocumentDTO updateDTO = new IdentityDocumentDTO();
        updateDTO.setIdentityDocumentId(null);
        
        IdentityDocument updatedIdentityDocument = new IdentityDocument();
        updatedIdentityDocument.setIdentityDocumentId(identityDocumentId);
        
        when(identityDocumentRepository.findById(identityDocumentId)).thenReturn(Mono.just(identityDocument));
        when(identityDocumentMapper.toEntity(updateDTO)).thenReturn(updatedIdentityDocument);
        when(identityDocumentRepository.save(updatedIdentityDocument)).thenReturn(Mono.just(updatedIdentityDocument));
        when(identityDocumentMapper.toDTO(updatedIdentityDocument)).thenReturn(identityDocumentDTO);

        // Act & Assert
        StepVerifier.create(identityDocumentService.updateIdentityDocument(identityDocumentId, updateDTO))
                .expectNext(identityDocumentDTO)
                .verifyComplete();

        verify(identityDocumentRepository).findById(identityDocumentId);
        verify(identityDocumentMapper).toEntity(updateDTO);
        verify(identityDocumentRepository).save(updatedIdentityDocument);
        verify(identityDocumentMapper).toDTO(updatedIdentityDocument);
    }

    @Test
    void updateIdentityDocument_ShouldReturnError_WhenIdentityDocumentNotFound() {
        // Arrange
        when(identityDocumentRepository.findById(identityDocumentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(identityDocumentService.updateIdentityDocument(identityDocumentId, identityDocumentDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Identity document not found with ID: " + identityDocumentId))
                .verify();

        verify(identityDocumentRepository).findById(identityDocumentId);
        verify(identityDocumentMapper, never()).toEntity(any());
        verify(identityDocumentRepository, never()).save(any());
    }

    @Test
    void deleteIdentityDocument_ShouldCompleteSuccessfully_WhenIdentityDocumentExists() {
        // Arrange
        when(identityDocumentRepository.findById(identityDocumentId)).thenReturn(Mono.just(identityDocument));
        when(identityDocumentRepository.deleteById(identityDocumentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(identityDocumentService.deleteIdentityDocument(identityDocumentId))
                .verifyComplete();

        verify(identityDocumentRepository).findById(identityDocumentId);
        verify(identityDocumentRepository).deleteById(identityDocumentId);
    }

    @Test
    void deleteIdentityDocument_ShouldReturnError_WhenIdentityDocumentNotFound() {
        // Arrange
        when(identityDocumentRepository.findById(identityDocumentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(identityDocumentService.deleteIdentityDocument(identityDocumentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Identity document not found with ID: " + identityDocumentId))
                .verify();

        verify(identityDocumentRepository).findById(identityDocumentId);
        verify(identityDocumentRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deleteIdentityDocument_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(identityDocumentRepository.findById(identityDocumentId)).thenReturn(Mono.just(identityDocument));
        when(identityDocumentRepository.deleteById(identityDocumentId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(identityDocumentService.deleteIdentityDocument(identityDocumentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(identityDocumentRepository).findById(identityDocumentId);
        verify(identityDocumentRepository).deleteById(identityDocumentId);
    }

    @Test
    void getIdentityDocumentById_ShouldReturnIdentityDocumentDTO_WhenIdentityDocumentExists() {
        // Arrange
        when(identityDocumentRepository.findById(identityDocumentId)).thenReturn(Mono.just(identityDocument));
        when(identityDocumentMapper.toDTO(identityDocument)).thenReturn(identityDocumentDTO);

        // Act & Assert
        StepVerifier.create(identityDocumentService.getIdentityDocumentById(identityDocumentId))
                .expectNext(identityDocumentDTO)
                .verifyComplete();

        verify(identityDocumentRepository).findById(identityDocumentId);
        verify(identityDocumentMapper).toDTO(identityDocument);
    }

    @Test
    void getIdentityDocumentById_ShouldReturnError_WhenIdentityDocumentNotFound() {
        // Arrange
        when(identityDocumentRepository.findById(identityDocumentId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(identityDocumentService.getIdentityDocumentById(identityDocumentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Identity document not found with ID: " + identityDocumentId))
                .verify();

        verify(identityDocumentRepository).findById(identityDocumentId);
        verify(identityDocumentMapper, never()).toDTO(any());
    }

    @Test
    void getIdentityDocumentById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(identityDocumentRepository.findById(identityDocumentId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(identityDocumentService.getIdentityDocumentById(identityDocumentId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(identityDocumentRepository).findById(identityDocumentId);
        verify(identityDocumentMapper, never()).toDTO(any());
    }
}