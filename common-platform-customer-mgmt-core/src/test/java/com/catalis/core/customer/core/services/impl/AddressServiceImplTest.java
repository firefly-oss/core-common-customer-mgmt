package com.catalis.core.customer.core.services.impl;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.customer.core.mappers.AddressMapper;
import com.catalis.core.customer.interfaces.dtos.AddressDTO;
import com.catalis.core.customer.models.entities.Address;
import com.catalis.core.customer.models.repositories.AddressRepository;
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
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private AddressDTO addressDTO;
    private Address address;
    private Long addressId;

    @BeforeEach
    void setUp() {
        addressId = 1L;
        
        address = new Address();
        address.setAddressId(addressId);
        address.setCreatedAt(LocalDateTime.now());
        address.setUpdatedAt(LocalDateTime.now());
        address.setPartyId(1L);

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
        doReturn(Mono.just(mockResponse)).when(spyService).filterAddresses(1L, filterRequest);

        // Act & Assert
        StepVerifier.create(spyService.filterAddresses(1L, filterRequest))
                .expectNext(mockResponse)
                .verifyComplete();

        // Verify that filterAddresses was called
        verify(spyService).filterAddresses(1L, filterRequest);
    }

    @Test
    void createAddress_ShouldReturnCreatedAddressDTO_WhenValidAddressDTO() {
        // Arrange
        when(addressMapper.toEntity(addressDTO)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(Mono.just(address));
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        // Act & Assert
        StepVerifier.create(addressService.createAddress(1L, addressDTO))
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
        StepVerifier.create(addressService.createAddress(1L, addressDTO))
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
        
        Address updatedAddress = new Address();
        updatedAddress.setAddressId(addressId);
        
        when(addressRepository.findById(addressId)).thenReturn(Mono.just(address));
        when(addressMapper.toEntity(updateDTO)).thenReturn(updatedAddress);
        when(addressRepository.save(updatedAddress)).thenReturn(Mono.just(updatedAddress));
        when(addressMapper.toDTO(updatedAddress)).thenReturn(addressDTO);

        // Act & Assert
        StepVerifier.create(addressService.updateAddress(1L, addressId, updateDTO))
                .expectNext(addressDTO)
                .verifyComplete();

        verify(addressRepository).findById(addressId);
        verify(addressMapper).toEntity(updateDTO);
        verify(addressRepository).save(updatedAddress);
        verify(addressMapper).toDTO(updatedAddress);
    }

    @Test
    void updateAddress_ShouldReturnError_WhenAddressNotFound() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(addressService.updateAddress(1L, addressId, addressDTO))
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
        StepVerifier.create(addressService.deleteAddress(1L, addressId))
                .verifyComplete();

        verify(addressRepository).findById(addressId);
        verify(addressRepository).deleteById(addressId);
    }

    @Test
    void deleteAddress_ShouldReturnError_WhenAddressNotFound() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(addressService.deleteAddress(1L, addressId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Address not found with ID: " + addressId))
                .verify();

        verify(addressRepository).findById(addressId);
        verify(addressRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deleteAddress_ShouldHandleRepositoryError_WhenDeleteFails() {
        // Arrange
        when(addressRepository.findById(addressId)).thenReturn(Mono.just(address));
        when(addressRepository.deleteById(addressId)).thenReturn(Mono.error(new RuntimeException("Delete failed")));

        // Act & Assert
        StepVerifier.create(addressService.deleteAddress(1L, addressId))
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
        StepVerifier.create(addressService.getAddressById(1L, addressId))
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
        StepVerifier.create(addressService.getAddressById(1L, addressId))
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
        StepVerifier.create(addressService.getAddressById(1L, addressId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && 
                        throwable.getMessage().equals("Database connection failed"))
                .verify();

        verify(addressRepository).findById(addressId);
        verify(addressMapper, never()).toDTO(any());
    }
}