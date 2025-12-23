package com.salon.user.payload.response.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class SalonDTO {

    private Long id;
    private String name;
    private List<String> images;
    private String address;
    private String phoneNumber;
    private String email;
    private String city;
    private Long ownerId;
    private UserDTO owner;
    private LocalTime openTime;
    private LocalTime closeTime;
}
