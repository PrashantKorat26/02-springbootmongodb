package com.skyorcus.repository;

import static org.junit.jupiter.api.Assertions.*;
import com.skyorcus.model.Transport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class TransportRepositoryTest {

    @Autowired
    private TransportRepository transportRepository;

    @Test
    void saveAndFindTransport() {
        // Given
        Transport transportToSave = createSampleTransport();

        // When
        Transport savedTransport = transportRepository.save(transportToSave);
        Transport foundTransport = transportRepository.findById(savedTransport.getId()).orElse(null);

        // Then
        assertThat(foundTransport).isNotNull();
        assertThat(foundTransport.getId()).isEqualTo(savedTransport.getId());
        assertThat(foundTransport.getFirstName()).isEqualTo(savedTransport.getFirstName());
        assertThat(foundTransport.getSecondName()).isEqualTo(savedTransport.getSecondName());
    }

    @Test
    void deleteTransport() {
        // Given
        Transport transportToSave = createSampleTransport();
        Transport savedTransport = transportRepository.save(transportToSave);

        // When
        transportRepository.deleteById(savedTransport.getId());
        Transport foundTransport = transportRepository.findById(savedTransport.getId()).orElse(null);

        // Then
        assertThat(foundTransport).isNull();
    }

    private Transport createSampleTransport() {
        return Transport.builder()
                .id(1)
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
