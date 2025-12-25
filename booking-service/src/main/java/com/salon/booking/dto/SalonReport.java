package com.salon.booking.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalonReport {

    private Long salonId;

    private String salonName;

    private int totalEarnings;

    private Integer totalBookings;

    private Integer cancelledBookings;

    private Double totalRefund;

}
