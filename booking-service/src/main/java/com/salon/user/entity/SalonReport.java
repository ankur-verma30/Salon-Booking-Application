package com.salon.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
