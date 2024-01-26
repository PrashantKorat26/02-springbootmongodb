package com.skyorcus.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyorcus.exception.ResourceNotFoundException;
import com.skyorcus.model.Transport;
import com.skyorcus.service.TransportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TransportControllerTest {

    @Mock
    private TransportService transportService;

    @InjectMocks
    private TransportController transportController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(transportController).build();

    @Test
    void saveBooking() throws Exception {
        Transport transport = createSampleTransport();
        when(transportService.saveBooking(any(Transport.class))).thenReturn(transport);

        mockMvc.perform(post("/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transport)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(transport.getId()))
                .andExpect(jsonPath("$.firstName").value(transport.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(transport.getSecondName()));
    }

    @Test
    void getAllBooking() throws Exception {
        Transport transport = createSampleTransport();
        List<Transport> transports = Collections.singletonList(transport);
        when(transportService.getAllBooking()).thenReturn(transports);

        mockMvc.perform(get("/detail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(transport.getId()))
                .andExpect(jsonPath("$[0].firstName").value(transport.getFirstName()))
                .andExpect(jsonPath("$[0].secondName").value(transport.getSecondName()));
    }

    @Test
    void getBookingById() throws Exception, ResourceNotFoundException {
        Transport transport = createSampleTransport();
        when(transportService.getBookingById(1L)).thenReturn(transport);

        mockMvc.perform(get("/booking/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transport.getId()))
                .andExpect(jsonPath("$.firstName").value(transport.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(transport.getSecondName()));
    }

    @Test
    void updateBooking() throws Exception, ResourceNotFoundException {
        Transport existingTransport = createSampleTransport();
        Transport updatedTransport = createSampleTransport();
        updatedTransport.setFirstName("UpdatedFirstName");

        when(transportService.updateBooking(any(Transport.class), eq(1L))).thenReturn(updatedTransport);

        mockMvc.perform(put("/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTransport)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedTransport.getId()))
                .andExpect(jsonPath("$.firstName").value(updatedTransport.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(updatedTransport.getSecondName()));
    }

    @Test
    void deleteBooking() throws Exception {
        mockMvc.perform(delete("/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking Cancelled Successfully "));
    }

    @Test
    void deleteBooking_resourceNotFoundException() throws Exception, ResourceNotFoundException {
        doThrow(ResourceNotFoundException.class).when(transportService).deleteBooking(1L);

        mockMvc.perform(delete("/1"))
                .andExpect(status().isNotFound());
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
