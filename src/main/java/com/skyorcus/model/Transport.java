package com.skyorcus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transport {


    @Id
    private long id;
    private String firstName;
    private String secondName;
    private String line;
    private long zone;
    private long age;
    private String city;
    private Date bookingDate;
    private String email;

}