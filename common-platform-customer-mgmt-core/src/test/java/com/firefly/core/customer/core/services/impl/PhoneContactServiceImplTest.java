package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.PhoneContactMapper;
import com.firefly.core.customer.interfaces.dtos.PhoneContactDTO;
import com.firefly.core.customer.models.entities.PhoneContact;
import com.firefly.core.customer.models.repositories.PhoneContactRepository;
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
class PhoneContactServiceImplTest {

    @Mock
    private PhoneContactRepository phoneContactRepository;

    @Mock
    private PhoneContactMapper phoneContactMapper;

    @InjectMocks
    private PhoneContactServiceImpl phoneContactService;

    private PhoneContactDTO phoneContactDTO;
    private PhoneContact phoneContact;
    private UUID phoneContactId;
    private UUID partyId;

    @BeforeEach
    void setUp() {
        phoneContactId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        partyId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        
        phoneContact = new PhoneContact();
        phoneContact.setPhoneContactId(phoneContactId);
        phoneContact.setCreatedAt(LocalDateTime.now());
        phoneContact.setUpdatedAt(LocalDateTime.now());

        phoneContactDTO = new PhoneContactDTO();
        phoneContactDTO.setPhoneContactId(phoneContactId);
        phoneContactDTO.setCreatedAt(LocalDateTime.now());
        phoneContactDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterPhoneContacts_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<PhoneContactDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<PhoneContactDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterPhoneContacts method
        PhoneContactServiceImpl spyService = spy(phoneContactService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterPhoneContacts(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterPhoneContacts(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterPhoneContacts was called
        verify(spyService).filterPhoneContacts(partyId, filterRequest);
    }

    @Test
    void createPhoneContact_ShouldReturnCreatedPhoneContactDTO_WhenValidPhoneContactDTO() {
        // Arrange
        when(phoneContactMapper.toEntity(phoneContactDTO)).thenReturn(phoneContact);
        when(phoneContactRepository.save(phoneContact)).thenReturn(Mono.just(phoneContact));
        when(phoneContactMapper.toDTO(phoneContact)).thenReturn(phoneContactDTO);

        // Act & Assert
        StepVerifier.create(phoneContactService.createPhoneContact(partyId, phoneContactDTO))
                .expectNext(phoneContactDTO)
                .verifyComplete();

        verify(phoneContactMapper).toEntity(phoneContactDTO);
        verify(phoneContactRepository).save(phoneContact);
        verify(phoneContactMapper).toDTO(phoneContact);
    }

    @Test
    void createPhoneContact_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(phoneContactMapper.toEntity(phoneContactDTO)).thenReturn(phoneContact);
        when(phoneContactRepository.save(phoneContact)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(phoneContactService.createPhoneContact(partyId, phoneContactDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(phoneContactMapper).toEntity(phoneContactDTO);
        verify(phoneContactRepository).save(phoneContact);
        verify(phoneContactMapper, never()).toDTO(any());
    }

    @Test
    void updatePhoneContact_ShouldReturnUpdatedPhoneContactDTO_WhenPhoneContactExists() {
        // Arrange
        PhoneContactDTO updateDTO = new PhoneContactDTO();
        updateDTO.setPhoneContactId(null);
        
        // Set up the existing phone contact with the correct partyId for validation
        phoneContact.setPartyId(partyId);
        
        when(phoneContactRepository.findById(phoneContactId)).thenReturn(Mono.just(phoneContact));
        doNothing().when(phoneContactMapper).updateEntityFromDto(updateDTO, phoneContact);
        when(phoneContactRepository.save(phoneContact)).thenReturn(Mono.just(phoneContact));
        when(phoneContactMapper.toDTO(phoneContact)).thenReturn(phoneContactDTO);

        // Act & Assert
        StepVerifier.create(phoneContactService.updatePhoneContact(partyId, phoneContactId, updateDTO))
                .expectNext(phoneContactDTO)
                .verifyComplete();

        verify(phoneContactRepository).findById(phoneContactId);
        verify(phoneContactMapper).updateEntityFromDto(updateDTO, phoneContact);
        verify(phoneContactRepository).save(phoneContact);
        verify(phoneContactMapper).toDTO(phoneContact);
    }

    @Test
    void updatePhoneContact_ShouldReturnError_WhenPhoneContactNotFound() {
        // Arrange
        when(phoneContactRepository.findById(phoneContactId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(phoneContactService.updatePhoneContact(partyId, phoneContactId, phoneContactDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Phone contact not found with ID: " + phoneContactId))
                .verify();

        verify(phoneContactRepository).findById(phoneContactId);
        verify(phoneContactMapper, never()).toEntity(any());
        verify(phoneContactRepository, never()).save(any());
    }

    @Test
    void deletePhoneContact_ShouldCompleteSuccessfully_WhenPhoneContactExists() {
        // Arrange
        when(phoneContactRepository.findById(phoneContactId)).thenReturn(Mono.just(phoneContact));
        when(phoneContactRepository.deleteById(phoneContactId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(phoneContactService.deletePhoneContact(partyId, phoneContactId))
                .verifyComplete();

        verify(phoneContactRepository).findById(phoneContactId);
        verify(phoneContactRepository).deleteById(phoneContactId);
    }

    @Test
    void deletePhoneContact_ShouldReturnError_WhenPhoneContactNotFound() {
        // Arrange
        when(phoneContactRepository.findById(phoneContactId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(phoneContactService.deletePhoneContact(partyId, phoneContactId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Phone contact not found with ID: " + phoneContactId))
                .verify();

        verify(phoneContactRepository).findById(phoneContactId);
        verify(phoneContactRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deletePhoneContact_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(phoneContactRepository.findById(phoneContactId)).thenReturn(Mono.just(phoneContact));
        when(phoneContactRepository.deleteById(phoneContactId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(phoneContactService.deletePhoneContact(partyId, phoneContactId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(phoneContactRepository).findById(phoneContactId);
        verify(phoneContactRepository).deleteById(phoneContactId);
    }

    @Test
    void getPhoneContactById_ShouldReturnPhoneContactDTO_WhenPhoneContactExists() {
        // Arrange
        when(phoneContactRepository.findById(phoneContactId)).thenReturn(Mono.just(phoneContact));
        when(phoneContactMapper.toDTO(phoneContact)).thenReturn(phoneContactDTO);

        // Act & Assert
        StepVerifier.create(phoneContactService.getPhoneContactById(partyId, phoneContactId))
                .expectNext(phoneContactDTO)
                .verifyComplete();

        verify(phoneContactRepository).findById(phoneContactId);
        verify(phoneContactMapper).toDTO(phoneContact);
    }

    @Test
    void getPhoneContactById_ShouldReturnError_WhenPhoneContactNotFound() {
        // Arrange
        when(phoneContactRepository.findById(phoneContactId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(phoneContactService.getPhoneContactById(partyId, phoneContactId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Phone contact not found with ID: " + phoneContactId))
                .verify();

        verify(phoneContactRepository).findById(phoneContactId);
        verify(phoneContactMapper, never()).toDTO(any());
    }

    @Test
    void getPhoneContactById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(phoneContactRepository.findById(phoneContactId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(phoneContactService.getPhoneContactById(partyId, phoneContactId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(phoneContactRepository).findById(phoneContactId);
        verify(phoneContactMapper, never()).toDTO(any());
    }
}