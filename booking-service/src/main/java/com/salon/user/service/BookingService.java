package com.salon.user.service;

import com.salon.user.domain.BookingStatus;
import com.salon.user.dto.BookingRequest;
import com.salon.user.dto.SalonDTO;
import com.salon.user.dto.ServiceDTO;
import com.salon.user.dto.UserDTO;
import com.salon.user.entity.Booking;
import com.salon.user.entity.PaymentOrder;
import com.salon.user.entity.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);

    List<Booking> getBookingsBySalon(Long salonId);

    Booking getBookingById(Long id) throws Exception;

    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;

    List<Booking> getBookingsByDate(LocalDate date, Long salonId);

    SalonReport getSalonReport(Long salonId);

    Booking bookingSucess(PaymentOrder order) throws Exception;

}
