/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.customer.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PoliticallyExposedPersonMapper;
import com.firefly.core.customer.interfaces.dtos.PoliticallyExposedPersonDTO;
import com.firefly.core.customer.models.entities.PoliticallyExposedPerson;
import com.firefly.core.customer.models.repositories.PoliticallyExposedPersonRepository;
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
class PoliticallyExposedPersonServiceImplTest {

    @Mock
    private PoliticallyExposedPersonRepository politicallyExposedPersonRepository;

    @Mock
    private PoliticallyExposedPersonMapper politicallyExposedPersonMapper;

    @InjectMocks
    private PoliticallyExposedPersonServiceImpl politicallyExposedPersonService;

    private PoliticallyExposedPersonDTO politicallyExposedPersonDTO;
    private PoliticallyExposedPerson politicallyExposedPerson;
    private UUID politicallyExposedPersonId;
    private UUID partyId;

    @BeforeEach
    void setUp() {
        politicallyExposedPersonId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        partyId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        
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
        doReturn(Mono.just(mockResponse)).when(spyService).filterPoliticallyExposedPersons(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPoliticallyExposedPersons(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPoliticallyExposedPersons was called
        verify(spyService).filterPoliticallyExposedPersons(partyId, filterRequest);
    }

    @Test
    void createPoliticallyExposedPerson_ShouldReturnCreatedPoliticallyExposedPersonDTO_WhenValidPoliticallyExposedPersonDTO() {
        // Arrange
        when(politicallyExposedPersonMapper.toEntity(politicallyExposedPersonDTO)).thenReturn(politicallyExposedPerson);
        when(politicallyExposedPersonRepository.save(politicallyExposedPerson)).thenReturn(Mono.just(politicallyExposedPerson));
        when(politicallyExposedPersonMapper.toDTO(politicallyExposedPerson)).thenReturn(politicallyExposedPersonDTO);

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.createPoliticallyExposedPerson(partyId, politicallyExposedPersonDTO))
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
        StepVerifier.create(politicallyExposedPersonService.createPoliticallyExposedPerson(partyId, politicallyExposedPersonDTO))
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
        StepVerifier.create(politicallyExposedPersonService.updatePoliticallyExposedPerson(partyId, politicallyExposedPersonId, updateDTO))
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
        StepVerifier.create(politicallyExposedPersonService.updatePoliticallyExposedPerson(partyId, politicallyExposedPersonId, politicallyExposedPersonDTO))
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
        StepVerifier.create(politicallyExposedPersonService.deletePoliticallyExposedPerson(partyId, politicallyExposedPersonId))
                .verifyComplete();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonRepository).deleteById(politicallyExposedPersonId);
    }

    @Test
    void deletePoliticallyExposedPerson_ShouldReturnError_WhenPoliticallyExposedPersonNotFound() {
        // Arrange
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.deletePoliticallyExposedPerson(partyId, politicallyExposedPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Politically exposed person not found with ID: " + politicallyExposedPersonId))
                .verify();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deletePoliticallyExposedPerson_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(politicallyExposedPersonRepository.findById(politicallyExposedPersonId)).thenReturn(Mono.just(politicallyExposedPerson));
        when(politicallyExposedPersonRepository.deleteById(politicallyExposedPersonId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(politicallyExposedPersonService.deletePoliticallyExposedPerson(partyId, politicallyExposedPersonId))
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
        StepVerifier.create(politicallyExposedPersonService.getPoliticallyExposedPersonById(partyId, politicallyExposedPersonId))
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
        StepVerifier.create(politicallyExposedPersonService.getPoliticallyExposedPersonById(partyId, politicallyExposedPersonId))
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
        StepVerifier.create(politicallyExposedPersonService.getPoliticallyExposedPersonById(partyId, politicallyExposedPersonId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(politicallyExposedPersonRepository).findById(politicallyExposedPersonId);
        verify(politicallyExposedPersonMapper, never()).toDTO(any());
    }
}