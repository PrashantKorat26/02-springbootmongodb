package com.skyorcus.service;

import static org.junit.jupiter.api.Assertions.*;
import com.skyorcus.exception.ResourceNotFoundException;
import com.skyorcus.model.Transport;
import com.skyorcus.repository.TransportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransportServiceImplTest {

    @Mock
    private TransportRepository transportRepository;

    @InjectMocks
    private TransportServiceImpl transportService;

    @Test
    void saveBooking() {
        Transport transportToSave = createSampleTransport();
        when(transportRepository.save(any(Transport.class))).thenReturn(transportToSave);

        Transport savedTransport = transportService.saveBooking(transportToSave);

        assertNotNull(savedTransport);
        assertEquals(transportToSave, savedTransport);
        verify(transportRepository, times(1)).save(any(Transport.class));
    }

    @Test
    void getAllBooking() {
        when(transportRepository.findAll()).thenReturn(Collections.emptyList());

        List<Transport> allBookings = transportService.getAllBooking();

        assertNotNull(allBookings);
        assertTrue(allBookings.isEmpty());
        verify(transportRepository, times(1)).findAll();
    }

    @Test
    void getBookingById_existingId() throws ResourceNotFoundException {
        long id = 1L;
        Transport existingTransport = createSampleTransport();
        when(transportRepository.findById(id)).thenReturn(java.util.Optional.of(existingTransport));

        Transport foundTransport = transportService.getBookingById(id);

        assertNotNull(foundTransport);
        assertEquals(existingTransport, foundTransport);
        verify(transportRepository, times(1)).findById(id);
    }

    @Test
    void getBookingById_nonExistingId() {
        long nonExistingId = 2L;
        when(transportRepository.findById(nonExistingId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transportService.getBookingById(nonExistingId));
        verify(transportRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void updateBooking_existingId() throws ResourceNotFoundException {
        long id = 1L;
        Transport existingTransport = createSampleTransport();
        when(transportRepository.findById(id)).thenReturn(java.util.Optional.of(existingTransport));
        when(transportRepository.save(any(Transport.class))).thenReturn(existingTransport);

        Transport updatedTransport = transportService.updateBooking(createSampleTransport(), id);

        assertNotNull(updatedTransport);
        assertEquals(existingTransport, updatedTransport);
        verify(transportRepository, times(1)).findById(id);
        verify(transportRepository, times(1)).save(any(Transport.class));
    }

    @Test
    void updateBooking_nonExistingId() {
        long nonExistingId = 2L;
        when(transportRepository.findById(nonExistingId)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transportService.updateBooking(createSampleTransport(), nonExistingId));
        verify(transportRepository, times(1)).findById(nonExistingId);
        verify(transportRepository, never()).save(any(Transport.class));
    }

    @Test
    void deleteBooking_existingId() throws ResourceNotFoundException {
        long id = 1L;
        when(transportRepository.existsById(id)).thenReturn(true);

        transportService.deleteBooking(id);

        verify(transportRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteBooking_nonExistingId() {
        long nonExistingId = 2L;
        when(transportRepository.existsById(nonExistingId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> transportService.deleteBooking(nonExistingId));
        verify(transportRepository, times(1)).existsById(nonExistingId);
        verify(transportRepository, never()).deleteById(any(Long.class));
    }

    private Transport createSampleTransport() {
        return Transport.builder()
                .id(1L)
                .firstName("John")
                .secondName("Doe")
                .line("Some Line")
                .zone(2L)
                .age(25L)
                .city("Some City")
                .bookingDate(new Date())
                .email("john.doe@example.com")
                .build();
    }
}
