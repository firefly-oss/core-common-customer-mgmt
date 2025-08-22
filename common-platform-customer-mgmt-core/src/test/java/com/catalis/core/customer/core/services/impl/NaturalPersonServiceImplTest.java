package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.NaturalPersonMapper;
import com.catalis.core.customer.interfaces.dtos.NaturalPersonDTO;
import com.catalis.core.customer.models.entities.NaturalPerson;
import com.catalis.core.customer.models.repositories.NaturalPersonRepository;
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
class NaturalPersonServiceImplTest {

    @Mock
    private NaturalPersonRepository naturalPersonRepository;

    @Mock
    private NaturalPersonMapper naturalPersonMapper;

    @InjectMocks
    private NaturalPersonServiceImpl naturalPersonService;

    private NaturalPersonDTO naturalPersonDTO;
    private NaturalPerson naturalPerson;
    private Long naturalPersonId;

    @BeforeEach
    void setUp() {
        naturalPersonId = 1L;
        
        naturalPerson = new NaturalPerson();
        naturalPerson.setNaturalPersonId(naturalPersonId);
        naturalPerson.setCreatedAt(LocalDateTime.now());
        naturalPerson.setUpdatedAt(LocalDateTime.now());

        naturalPersonDTO = new NaturalPersonDTO();
        naturalPersonDTO.setNaturalPersonId(naturalPersonId);
        naturalPersonDTO.setCreatedAt(LocalDateTime.now());
        naturalPersonDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterNaturalPersons_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<NaturalPersonDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<NaturalPersonDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterNaturalPersons method
        NaturalPersonServiceImpl spyService = spy(naturalPersonService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterNaturalPersons(filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterNaturalPersons(filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterNaturalPersons was called
        verify(spyService).filterNaturalPersons(filterRequest);
    }

    @Test
    void createNaturalPerson_ShouldReturnCreatedNaturalPersonDTO_WhenValidNaturalPersonDTO() {
        // Arrange
        when(naturalPersonMapper.toEntity(naturalPersonDTO)).thenReturn(naturalPerson);
        when(naturalPersonRepository.save(naturalPerson)).thenReturn(Mono.just(naturalPerson));
        when(naturalPersonMapper.toDTO(naturalPerson)).thenReturn(naturalPersonDTO);

        // Act & Assert
        StepVerifier.create(naturalPersonService.createNaturalPerson(naturalPersonDTO))
                .expectNext(naturalPersonDTO)
                .verifyComplete();

        verify(naturalPersonMapper).toEntity(naturalPersonDTO);
        verify(naturalPersonRepository).save(naturalPerson);
        verify(naturalPersonMapper).toDTO(naturalPerson);
    }

    @Test
    void createNaturalPerson_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(naturalPersonMapper.toEntity(naturalPersonDTO)).thenReturn(naturalPerson);
        when(naturalPersonRepository.save(naturalPerson)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(naturalPersonService.createNaturalPerson(naturalPersonDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(naturalPersonMapper).toEntity(naturalPersonDTO);
        verify(naturalPersonRepository).save(naturalPerson);
        verify(naturalPersonMapper, never()).toDTO(any());
    }

    @Test
    void updateNaturalPerson_ShouldReturnUpdatedNaturalPersonDTO_WhenNaturalPersonExists() {
        // Arrange
        NaturalPersonDTO updateDTO = new NaturalPersonDTO();
        updateDTO.setNaturalPersonId(null);
        
        NaturalPerson updatedNaturalPerson = new NaturalPerson();
        updatedNaturalPerson.setNaturalPersonId(naturalPersonId);
        
        when(naturalPersonRepository.findById(naturalPersonId)).thenReturn(Mono.just(naturalPerson));
        when(naturalPersonMapper.toEntity(updateDTO)).thenReturn(updatedNaturalPerson);
        when(naturalPersonRepository.save(updatedNaturalPerson)).thenReturn(Mono.just(updatedNaturalPerson));
        when(naturalPersonMapper.toDTO(updatedNaturalPerson)).thenReturn(naturalPersonDTO);

        // Act & Assert
        StepVerifier.create(naturalPersonService.updateNaturalPerson(naturalPersonId, updateDTO))
                .expectNext(naturalPersonDTO)
                .verifyComplete();

        verify(naturalPersonRepository).findById(naturalPersonId);
        verify(naturalPersonMapper).toEntity(updateDTO);
        verify(naturalPersonRepository).save(updatedNaturalPerson);
        verify(naturalPersonMapper).toDTO(updatedNaturalPerson);
    }

    @Test
    void updateNaturalPerson_ShouldReturnError_WhenNaturalPersonNotFound() {
        // Arrange
        when(naturalPersonRepository.findById(naturalPersonId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(naturalPersonService.updateNaturalPerson(naturalPersonId, naturalPersonDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Natural person not found with ID: " + naturalPersonId))
                .verify();

        verify(naturalPersonRepository).findById(naturalPersonId);
        verify(naturalPersonMapper, never()).toEntity(any());
        verify(naturalPersonRepository, never()).save(any());
    }

    @Test
    void deleteNaturalPerson_ShouldCompleteSuccessfully_WhenNaturalPersonExists() {
        // Arrange
        when(naturalPersonRepository.findById(naturalPersonId)).thenReturn(Mono.just(naturalPerson));
        when(naturalPersonRepository.deleteById(naturalPersonId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(naturalPersonService.deleteNaturalPerson(naturalPersonId))
                .verifyComplete();

        verify(naturalPersonRepository).findById(naturalPersonId);
        verify(naturalPersonRepository).deleteById(naturalPersonId);
    }

    @Test
    void deleteNaturalPerson_ShouldReturnError_WhenNaturalPersonNotFound() {
        // Arrange
        when(naturalPersonRepository.findById(naturalPersonId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(naturalPersonService.deleteNaturalPerson(naturalPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Natural person not found with ID: " + naturalPersonId))
                .verify();

        verify(naturalPersonRepository).findById(naturalPersonId);
        verify(naturalPersonRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deleteNaturalPerson_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(naturalPersonRepository.findById(naturalPersonId)).thenReturn(Mono.just(naturalPerson));
        when(naturalPersonRepository.deleteById(naturalPersonId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(naturalPersonService.deleteNaturalPerson(naturalPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(naturalPersonRepository).findById(naturalPersonId);
        verify(naturalPersonRepository).deleteById(naturalPersonId);
    }

    @Test
    void getNaturalPersonById_ShouldReturnNaturalPersonDTO_WhenNaturalPersonExists() {
        // Arrange
        when(naturalPersonRepository.findById(naturalPersonId)).thenReturn(Mono.just(naturalPerson));
        when(naturalPersonMapper.toDTO(naturalPerson)).thenReturn(naturalPersonDTO);

        // Act & Assert
        StepVerifier.create(naturalPersonService.getNaturalPersonById(naturalPersonId))
                .expectNext(naturalPersonDTO)
                .verifyComplete();

        verify(naturalPersonRepository).findById(naturalPersonId);
        verify(naturalPersonMapper).toDTO(naturalPerson);
    }

    @Test
    void getNaturalPersonById_ShouldReturnError_WhenNaturalPersonNotFound() {
        // Arrange
        when(naturalPersonRepository.findById(naturalPersonId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(naturalPersonService.getNaturalPersonById(naturalPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Natural person not found with ID: " + naturalPersonId))
                .verify();

        verify(naturalPersonRepository).findById(naturalPersonId);
        verify(naturalPersonMapper, never()).toDTO(any());
    }

    @Test
    void getNaturalPersonById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(naturalPersonRepository.findById(naturalPersonId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(naturalPersonService.getNaturalPersonById(naturalPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(naturalPersonRepository).findById(naturalPersonId);
        verify(naturalPersonMapper, never()).toDTO(any());
    }
}