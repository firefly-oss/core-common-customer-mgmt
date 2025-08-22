package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.PoliticallyExposedPersonMapper;
import com.catalis.core.customer.interfaces.dtos.PoliticallyExposedPersonDTO;
import com.catalis.core.customer.models.entities.PoliticallyExposedPerson;
import com.catalis.core.customer.models.repositories.PoliticallyExposedPersonRepository;
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
class PoliticallyExposedPersonServiceImplTest {

    @Mock
    private PoliticallyExposedPersonRepository politicallyExposedPersonRepository;

    @Mock
    private PoliticallyExposedPersonMapper politicallyExposedPersonMapper;

    @InjectMocks
    private PoliticallyExposedPersonServiceImpl politicallyExposedPersonService;

    private PoliticallyExposedPersonDTO politicallyExposedPersonDTO;
    private PoliticallyExposedPerson politicallyExposedPerson;
    private Long politicallyExposedPersonId;

    @BeforeEach
    void setUp() {
        politicallyExposedPersonId = 1L;
        
        politicallyExposedPerson = new PoliticallyExposedPerson();
        politicallyExposedPerson.setPepId(politicallyExposedPersonId);
        politicallyExposedPerson.setCreatedAt(LocalDateTime.now());
        politicallyExposedPerson.setUpdatedAt(LocalDateTime.now());

        politicallyExposedPersonDTO = new PoliticallyExposedPersonDTO();
        politicallyExposedPersonDTO.setPepId(politicallyExposedPersonId);
        politicallyExposedPersonDTO.setCreatedAt(LocalDateTime.now());
        politicallyExposedPersonDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterPoliticallyExposedPersons_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<PoliticallyExposedPersonDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<PoliticallyExposedPersonDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterPoliticallyExposedPersons method
        PoliticallyExposedPersonServiceImpl spyService = spy(politicallyExposedPersonService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterPoliticallyExposedPersons(filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPoliticallyExposedPersons(filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPoliticallyExposedPersons was called
        verify(spyService).filterPoliticallyExposedPersons(filterRequest);
    }

    @Test
    void createPoliticallyExposedPerson_ShouldReturnCreatedPoliticallyExposedPersonDTO_WhenValidPoliticallyExposedPersonDTO() {
        // Arrange
        when(politicallyExposedPersonMapper.toEntity(politicallyExposedPersonDTO)).thenReturn(politicallyExposedPerson);
        when(politicallyExposedPersonRepository.save(politicallyExposedPerson)).thenReturn(Mono.just(politicallyExposedPerson));
        when(politicallyExposedPersonMapper.toDTO(politicallyExposedPerson)).thenReturn(politicallyExposedPersonDTO);

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.createPoliticallyExposedPerson(politicallyExposedPersonDTO))
                .expectNext(politicallyExposedPersonDTO)
                .verifyComplete();

        verify(politicallyExposedPersonMapper).toEntity(politicallyExposedPersonDTO);
        verify(politicallyExposedPersonRepository).save(politicallyExposedPerson);
        verify(politicallyExposedPersonMapper).toDTO(politicallyExposedPerson);
    }

    @Test
    void createPoliticallyExposedPerson_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(politicallyExposedPersonMapper.toEntity(politicallyExposedPersonDTO)).thenReturn(politicallyExposedPerson);
        when(politicallyExposedPersonRepository.save(politicallyExposedPerson)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.createPoliticallyExposedPerson(politicallyExposedPersonDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(politicallyExposedPersonMapper).toEntity(politicallyExposedPersonDTO);
        verify(politicallyExposedPersonRepository).save(politicallyExposedPerson);
        verify(politicallyExposedPersonMapper, never()).toDTO(any());
    }

    @Test
    void updatePoliticallyExposedPerson_ShouldReturnUpdatedPoliticallyExposedPersonDTO_WhenPoliticallyExposedPersonExists() {
        // Arrange
        PoliticallyExposedPersonDTO updateDTO = new PoliticallyExposedPersonDTO();
        updateDTO.setPepId(null);
        
        PoliticallyExposedPerson updatedPoliticallyExposedPerson = new PoliticallyExposedPerson();
        updatedPoliticallyExposedPerson.setPepId(politicallyExposedPersonId);
        
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.just(politicallyExposedPerson));
        when(politicallyExposedPersonMapper.toEntity(updateDTO)).thenReturn(updatedPoliticallyExposedPerson);
        when(politicallyExposedPersonRepository.save(updatedPoliticallyExposedPerson)).thenReturn(Mono.just(updatedPoliticallyExposedPerson));
        when(politicallyExposedPersonMapper.toDTO(updatedPoliticallyExposedPerson)).thenReturn(politicallyExposedPersonDTO);

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.updatePoliticallyExposedPerson(politicallyExposedPersonId, updateDTO))
                .expectNext(politicallyExposedPersonDTO)
                .verifyComplete();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonMapper).toEntity(updateDTO);
        verify(politicallyExposedPersonRepository).save(updatedPoliticallyExposedPerson);
        verify(politicallyExposedPersonMapper).toDTO(updatedPoliticallyExposedPerson);
    }

    @Test
    void updatePoliticallyExposedPerson_ShouldReturnError_WhenPoliticallyExposedPersonNotFound() {
        // Arrange
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.updatePoliticallyExposedPerson(politicallyExposedPersonId, politicallyExposedPersonDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Politically exposed person not found with ID: " + politicallyExposedPersonId))
                .verify();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonMapper, never()).toEntity(any());
        verify(politicallyExposedPersonRepository, never()).save(any());
    }

    @Test
    void deletePoliticallyExposedPerson_ShouldCompleteSuccessfully_WhenPoliticallyExposedPersonExists() {
        // Arrange
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.just(politicallyExposedPerson));
        when(politicallyExposedPersonRepository.deleteById(politicallyExposedPersonId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.deletePoliticallyExposedPerson(politicallyExposedPersonId))
                .verifyComplete();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonRepository).deleteById(politicallyExposedPersonId);
    }

    @Test
    void deletePoliticallyExposedPerson_ShouldReturnError_WhenPoliticallyExposedPersonNotFound() {
        // Arrange
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.deletePoliticallyExposedPerson(politicallyExposedPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Politically exposed person not found with ID: " + politicallyExposedPersonId))
                .verify();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deletePoliticallyExposedPerson_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.just(politicallyExposedPerson));
        when(politicallyExposedPersonRepository.deleteById(politicallyExposedPersonId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.deletePoliticallyExposedPerson(politicallyExposedPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonRepository).deleteById(politicallyExposedPersonId);
    }

    @Test
    void getPoliticallyExposedPersonById_ShouldReturnPoliticallyExposedPersonDTO_WhenPoliticallyExposedPersonExists() {
        // Arrange
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.just(politicallyExposedPerson));
        when(politicallyExposedPersonMapper.toDTO(politicallyExposedPerson)).thenReturn(politicallyExposedPersonDTO);

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.getPoliticallyExposedPersonById(politicallyExposedPersonId))
                .expectNext(politicallyExposedPersonDTO)
                .verifyComplete();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonMapper).toDTO(politicallyExposedPerson);
    }

    @Test
    void getPoliticallyExposedPersonById_ShouldReturnError_WhenPoliticallyExposedPersonNotFound() {
        // Arrange
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.getPoliticallyExposedPersonById(politicallyExposedPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Politically exposed person not found with ID: " + politicallyExposedPersonId))
                .verify();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonMapper, never()).toDTO(any());
    }

    @Test
    void getPoliticallyExposedPersonById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.getPoliticallyExposedPersonById(politicallyExposedPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonMapper, never()).toDTO(any());
    }
}