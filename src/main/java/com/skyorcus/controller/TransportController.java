package com.skyorcus.controller;

import com.skyorcus.exception.ResourceNotFoundException;
import com.skyorcus.model.Transport;
import com.skyorcus.service.TransportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TransportController {

    private final TransportService transportService;

    public TransportController(TransportService transportService) {
        super();
        this.transportService = transportService;
    }


    @PostMapping("/reserve")
    public ResponseEntity<Transport> saveBooking(@RequestBody Transport transport) {
        return new ResponseEntity<Transport>(transportService.saveBooking(transport), HttpStatus.CREATED);
    }



    @GetMapping("/detail")
    public List<Transport> getAllBooking() {
        return transportService.getAllBooking();
    }


    @GetMapping("booking/{id}")
    public ResponseEntity<Transport> getBookingById(@PathVariable("id") long employeeId) throws ResourceNotFoundException {
        return new ResponseEntity<Transport>(transportService.getBookingById(employeeId), HttpStatus.OK);

    }


    @PutMapping("{id}")
    public ResponseEntity<Transport> updateBooking(@PathVariable("id") long id,
                                                    @RequestBody Transport transport) throws ResourceNotFoundException {
        return new ResponseEntity<Transport>(transportService.updateBooking(transport, id), HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") long id) throws ResourceNotFoundException {
        transportService.deleteBooking(id);
        return new ResponseEntity<String>("Booking Cancelled Successfully ", HttpStatus.OK);

    }
}