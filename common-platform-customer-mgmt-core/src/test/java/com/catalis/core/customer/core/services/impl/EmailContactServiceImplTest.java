package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.EmailContactMapper;
import com.catalis.core.customer.interfaces.dtos.EmailContactDTO;
import com.catalis.core.customer.models.entities.EmailContact;
import com.catalis.core.customer.models.repositories.EmailContactRepository;
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
class EmailContactServiceImplTest {

    @Mock
    private EmailContactRepository emailContactRepository;

    @Mock
    private EmailContactMapper emailContactMapper;

    @InjectMocks
    private EmailContactServiceImpl emailContactService;

    private EmailContactDTO emailContactDTO;
    private EmailContact emailContact;
    private Long emailContactId;
    private Long partyId;

    @BeforeEach
    void setUp() {
        emailContactId = 1L;
        partyId = 1L;
        
        emailContact = new EmailContact();
        emailContact.setEmailContactId(emailContactId);
        emailContact.setCreatedAt(LocalDateTime.now());
        emailContact.setUpdatedAt(LocalDateTime.now());

        emailContactDTO = new EmailContactDTO();
        emailContactDTO.setEmailContactId(emailContactId);
        emailContactDTO.setCreatedAt(LocalDateTime.now());
        emailContactDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterEmailContacts_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<EmailContactDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<EmailContactDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterEmailContacts method
        EmailContactServiceImpl spyService = spy(emailContactService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterEmailContacts(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterEmailContacts(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterEmailContacts was called
        verify(spyService).filterEmailContacts(partyId, filterRequest);
    }

    @Test
    void createEmailContact_ShouldReturnCreatedEmailContactDTO_WhenValidEmailContactDTO() {
        // Arrange
        when(emailContactMapper.toEntity(emailContactDTO)).thenReturn(emailContact);
        when(emailContactRepository.save(emailContact)).thenReturn(Mono.just(emailContact));
        when(emailContactMapper.toDTO(emailContact)).thenReturn(emailContactDTO);

        // Act & Assert
        StepVerifier.create(emailContactService.createEmailContact(partyId, emailContactDTO))
                .expectNext(emailContactDTO)
                .verifyComplete();

        verify(emailContactMapper).toEntity(emailContactDTO);
        verify(emailContactRepository).save(emailContact);
        verify(emailContactMapper).toDTO(emailContact);
    }

    @Test
    void createEmailContact_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(emailContactMapper.toEntity(emailContactDTO)).thenReturn(emailContact);
        when(emailContactRepository.save(emailContact)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(emailContactService.createEmailContact(partyId, emailContactDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(emailContactMapper).toEntity(emailContactDTO);
        verify(emailContactRepository).save(emailContact);
        verify(emailContactMapper, never()).toDTO(any());
    }

    @Test
    void updateEmailContact_ShouldReturnUpdatedEmailContactDTO_WhenEmailContactExists() {
        // Arrange
        EmailContactDTO updateDTO = new EmailContactDTO();
        updateDTO.setEmailContactId(null);
        
        EmailContact updatedEmailContact = new EmailContact();
        updatedEmailContact.setEmailContactId(emailContactId);
        
        when(emailContactRepository.findById(emailContactId)).thenReturn(Mono.just(emailContact));
        when(emailContactMapper.toEntity(updateDTO)).thenReturn(updatedEmailContact);
        when(emailContactRepository.save(updatedEmailContact)).thenReturn(Mono.just(updatedEmailContact));
        when(emailContactMapper.toDTO(updatedEmailContact)).thenReturn(emailContactDTO);

        // Act & Assert
        StepVerifier.create(emailContactService.updateEmailContact(partyId, emailContactId, updateDTO))
                .expectNext(emailContactDTO)
                .verifyComplete();

        verify(emailContactRepository).findById(emailContactId);
        verify(emailContactMapper).toEntity(updateDTO);
        verify(emailContactRepository).save(updatedEmailContact);
        verify(emailContactMapper).toDTO(updatedEmailContact);
    }

    @Test
    void updateEmailContact_ShouldReturnError_WhenEmailContactNotFound() {
        // Arrange
        when(emailContactRepository.findById(emailContactId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(emailContactService.updateEmailContact(partyId, emailContactId, emailContactDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Email contact not found with ID: " + emailContactId))
                .verify();

        verify(emailContactRepository).findById(emailContactId);
        verify(emailContactMapper, never()).toEntity(any());
        verify(emailContactRepository, never()).save(any());
    }

    @Test
    void deleteEmailContact_ShouldCompleteSuccessfully_WhenEmailContactExists() {
        // Arrange
        when(emailContactRepository.findById(emailContactId)).thenReturn(Mono.just(emailContact));
        when(emailContactRepository.deleteById(emailContactId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(emailContactService.deleteEmailContact(partyId, emailContactId))
                .verifyComplete();

        verify(emailContactRepository).findById(emailContactId);
        verify(emailContactRepository).deleteById(emailContactId);
    }

    @Test
    void deleteEmailContact_ShouldReturnError_WhenEmailContactNotFound() {
        // Arrange
        when(emailContactRepository.findById(emailContactId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(emailContactService.deleteEmailContact(partyId, emailContactId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Email contact not found with ID: " + emailContactId))
                .verify();

        verify(emailContactRepository).findById(emailContactId);
        verify(emailContactRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deleteEmailContact_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(emailContactRepository.findById(emailContactId)).thenReturn(Mono.just(emailContact));
        when(emailContactRepository.deleteById(emailContactId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(emailContactService.deleteEmailContact(partyId, emailContactId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(emailContactRepository).findById(emailContactId);
        verify(emailContactRepository).deleteById(emailContactId);
    }

    @Test
    void getEmailContactById_ShouldReturnEmailContactDTO_WhenEmailContactExists() {
        // Arrange
        when(emailContactRepository.findById(emailContactId)).thenReturn(Mono.just(emailContact));
        when(emailContactMapper.toDTO(emailContact)).thenReturn(emailContactDTO);

        // Act & Assert
        StepVerifier.create(emailContactService.getEmailContactById(partyId, emailContactId))
                .expectNext(emailContactDTO)
                .verifyComplete();

        verify(emailContactRepository).findById(emailContactId);
        verify(emailContactMapper).toDTO(emailContact);
    }

    @Test
    void getEmailContactById_ShouldReturnError_WhenEmailContactNotFound() {
        // Arrange
        when(emailContactRepository.findById(emailContactId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(emailContactService.getEmailContactById(partyId, emailContactId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Email contact not found with ID: " + emailContactId))
                .verify();

        verify(emailContactRepository).findById(emailContactId);
        verify(emailContactMapper, never()).toDTO(any());
    }

    @Test
    void getEmailContactById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(emailContactRepository.findById(emailContactId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(emailContactService.getEmailContactById(partyId, emailContactId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(emailContactRepository).findById(emailContactId);
        verify(emailContactMapper, never()).toDTO(any());
    }
}