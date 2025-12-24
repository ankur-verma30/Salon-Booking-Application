package com.salon.booking.service;

import com.salon.booking.domain.BookingStatus;
import com.salon.booking.dto.BookingRequest;
import com.salon.booking.dto.SalonDTO;
import com.salon.booking.dto.ServiceDTO;
import com.salon.booking.dto.UserDTO;
import com.salon.booking.entity.Booking;
import com.salon.booking.entity.PaymentOrder;
import com.salon.booking.entity.SalonReport;

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
