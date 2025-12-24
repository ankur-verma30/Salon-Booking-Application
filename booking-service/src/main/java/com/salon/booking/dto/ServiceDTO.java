package com.salon.booking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDTO {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int duration;
    private Long salonId;
    private Long category;
    private String image;
}
