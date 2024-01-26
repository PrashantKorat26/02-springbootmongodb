package com.skyorcus.service;

import com.skyorcus.exception.ResourceNotFoundException;
import com.skyorcus.model.Transport;
import com.skyorcus.repository.TransportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;

    public TransportServiceImpl(TransportRepository transportRepository) {
        super();
        this.transportRepository = transportRepository;
    }

    @Override
    public Transport saveBooking(Transport transport) {
        return transportRepository.save(transport);
    }

    @Override
    public List<Transport> getAllBooking() {
        return transportRepository.findAll();
    }

    @Override
    public Transport getBookingById(long id) throws ResourceNotFoundException {
        Optional<Transport> employee = transportRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new ResourceNotFoundException("Employee", "id", id);
        }

    }

    @Override
    public Transport updateBooking(Transport transport, long id) throws ResourceNotFoundException {
        Transport existingTransport = transportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id));
        existingTransport.setFirstName(transport.getFirstName());
        existingTransport.setSecondName((transport.getSecondName()));
        existingTransport.setEmail(transport.getEmail());
        existingTransport.setLine(transport.getLine());
        existingTransport.setZone(transport.getZone());
        existingTransport.setCity(transport.getCity());
        existingTransport.setAge(transport.getAge());
        existingTransport.setBookingDate(transport.getBookingDate());

        transportRepository.save(existingTransport);
        return existingTransport;
    }

    @Override
    public void deleteBooking(long id) throws ResourceNotFoundException {
        transportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id));
        transportRepository.deleteById(id);
    }

}
