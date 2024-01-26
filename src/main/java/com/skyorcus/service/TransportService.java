package com.skyorcus.service;

import com.skyorcus.exception.ResourceNotFoundException;
import com.skyorcus.model.Transport;

import java.util.List;

public interface TransportService {

    Transport saveBooking(Transport transport);

    List<Transport> getAllBooking();

    Transport getBookingById(long id) throws ResourceNotFoundException;

    Transport updateBooking(Transport transport, long id) throws ResourceNotFoundException;

    void deleteBooking(long id) throws ResourceNotFoundException;
}
