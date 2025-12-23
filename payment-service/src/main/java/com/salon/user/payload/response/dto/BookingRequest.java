package com.salon.user.payload.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class BookingRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<Long> serviceIds;

}
