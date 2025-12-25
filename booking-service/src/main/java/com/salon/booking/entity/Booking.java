package com.salon.booking.entity;

import com.salon.booking.domain.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long salonId;

    private Long customerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ElementCollection //what does this do?
    private Set<Long> serviceIds;

    private BookingStatus status=BookingStatus.PENDING;

    private int totalPrice;

}
