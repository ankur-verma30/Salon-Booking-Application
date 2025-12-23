package com.salon.user.payload.response.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {


    private Long id;

    private String type;

    private Boolean isRead=false;

    private String description;

    private Long userId;

    private Long bookingId;

    private Long salonId;

    private LocalDateTime createdAt;

    private BookingDTO booking;
}
