package com.firefly.core.customer.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.customer.core.mappers.AddressMapper;
import com.firefly.core.customer.interfaces.dtos.AddressDTO;
import com.firefly.core.customer.models.entities.Address;
import com.firefly.core.customer.models.repositories.AddressRepository;
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
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private AddressDTO addressDTO;
    private Address address;
    private UUID addressId;
    private UUID partyId;

    @BeforeEach
    void setUp() {
        addressId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        partyId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");

        address = new Address();
        address.setAddressId(addressId);
        address.setCreatedAt(LocalDateTime.now());
        address.setUpdatedAt(LocalDateTime.now());
        address.setPartyId(partyId);

        addressDTO = new AddressDTO();
        addressDTO.setAddressId(addressId);
        addressDTO.setCreatedAt(LocalDateTime.now());
        addressDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void filterAddresses_ShouldReturnFilteredResults_WhenValidFilterRequest() {
        // Arrange
        FilterRequest<AddressDTO> filterRequest = new FilterRequest<>();
        PaginationResponse<AddressDTO> mockResponse = mock(PaginationResponse.class);
        
        // Create a spy of the service to mock the filterAddresses method
        AddressServiceImpl spyService = spy(addressService);
        doReturn(Mono.just(mockResponse)).when(spyService).filterAddresses(partyId, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterAddresses(partyId, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterAddresses was called
        verify(spyService).filterAddresses(partyId, filterRequest);
    }

    @Test
    void createAddress_ShouldReturnCreatedAddressDTO_WhenValidAddressDTO() {
        // Arrange
        when(addressMapper.toEntity(addressDTO)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(Mono.just(address));
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        // Act & Assert
        StepVerifier.create(addressService.createAddress(partyId, addressDTO))
                .expectNext(addressDTO)
                .verifyComplete();

        verify(addressMapper).toEntity(addressDTO);
        verify(addressRepository).save(address);
        verify(addressMapper).toDTO(address);
    }

    @Test
    void createAddress_ShouldHandleRepositoryError_WhenSaveFails() {
        // Arrange
        when(addressMapper.toEntity(addressDTO)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(addressService.createAddress(partyId, addressDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database error"))
                .verify();

        verify(addressMapper).toEntity(addressDTO);
        verify(addressRepository).save(address);
        verify(addressMapper, never()).toDTO(any());
    }

    @Test
    void updateAddress_ShouldReturnUpdatedAddressDTO_WhenAddressExists() {
        // Arrange
        AddressDTO updateDTO = new AddressDTO();
        updateDTO.setAddressId(null);
        
        // Set up the existing address with the correct partyId for validation
        address.setPartyId(partyId);

        when(addressRepository.findById(addressId)).thenReturn(Mono.just(address));
        doNothing().when(addressMapper).updateEntityFromDto(updateDTO, address);
        when(addressRepository.save(address)).thenReturn(Mono.just(address));
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        // Act & Assert
        StepVerifier.create(addressService.updateAddress(partyId, addressId, updateDTO))
                .expectNext(addressDTO)
                .verifyComplete();

        verify(addressRepository).findById(addressId);
        verify(addressMapper).updateEntityFromDto(updateDTO, address);
        verify(addressRepository).save(address);
        verify(addressMapper).toDTO(address);
    }

    @Test
    void updateAddress_ShouldReturnError_WhenAddressNotFound() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(addressService.updateAddress(partyId, addressId, addressDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Address not found with ID: " + addressId))
                .verify();

        verify(addressRepository).findById(addressId);
        verify(addressMapper, never()).toEntity(any());
        verify(addressRepository, never()).save(any());
    }

    @Test
    void deleteAddress_ShouldCompleteSuccessfully_WhenAddressExists() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.just(address));
        when(addressRepository.deleteById(addressId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(addressService.deleteAddress(partyId, addressId))
                .verifyComplete();

        verify(addressRepository).findById(addressId);
        verify(addressRepository).deleteById(addressId);
    }

    @Test
    void deleteAddress_ShouldReturnError_WhenAddressNotFound() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(addressService.deleteAddress(partyId, addressId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Address not found with ID: " + addressId))
                .verify();

        verify(addressRepository).findById(addressId);
        verify(addressRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteAddress_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.just(address));
        when(addressRepository.deleteById(addressId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(addressService.deleteAddress(partyId, addressId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Delete failed"))
                .verify();

        verify(addressRepository).findById(addressId);
        verify(addressRepository).deleteById(addressId);
    }

    @Test
    void getAddressById_ShouldReturnAddressDTO_WhenAddressExists() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.just(address));
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        // Act & Assert
        StepVerifier.create(addressService.getAddressById(partyId, addressId))
                .expectNext(addressDTO)
                .verifyComplete();

        verify(addressRepository).findById(addressId);
        verify(addressMapper).toDTO(address);
    }

    @Test
    void getAddressById_ShouldReturnError_WhenAddressNotFound() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(addressService.getAddressById(partyId, addressId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Address not found with ID: " + addressId))
                .verify();

        verify(addressRepository).findById(addressId);
        verify(addressMapper, never()).toDTO(any());
    }

    @Test
    void getAddressById_ShouldHandleRepositoryError_WhenFindFails() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.error(new RuntimeException("Database connection failed")));

        // Act & Assert
        StepVerifier.create(addressService.getAddressById(partyId, addressId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(addressRepository).findById(addressId);
        verify(addressMapper, never()).toDTO(any());
    }
}