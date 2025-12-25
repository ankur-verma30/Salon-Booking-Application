package com.salon.booking.service.impl;

import com.salon.booking.domain.BookingStatus;
import com.salon.booking.dto.BookingRequest;
import com.salon.booking.dto.SalonDTO;
import com.salon.booking.dto.ServiceDTO;
import com.salon.booking.dto.UserDTO;
import com.salon.booking.entity.Booking;
import com.salon.booking.entity.PaymentOrder;
import com.salon.booking.dto.SalonReport;
import com.salon.booking.repository.BookingRepository;
import com.salon.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> serviceDTOSet) throws Exception {
        int totalDuration = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getDuration)
                .sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        int totalPrice = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getPrice)
                .sum();

        Set<Long> idList = serviceDTOSet.stream()
                .map(ServiceDTO::getId)
                .collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(userDTO.getId());
        newBooking.setSalonId(salonDTO.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime) throws Exception {

        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getCloseTime().atDate(bookingStartTime.toLocalDate());

        // Check if within working hours
        if (bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)) {
            throw new Exception("Booking time must be within salon's working hours");
        }

        // Fetch existing bookings
        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());

        // Check overlap
        for (Booking existingBooking : existingBookings) {

            LocalDateTime existingStart = existingBooking.getStartTime();
            LocalDateTime existingEnd = existingBooking.getEndTime();

            if (bookingStartTime.isBefore(existingEnd) &&
                    bookingEndTime.isAfter(existingStart)) {
                throw new Exception("Slot not available choose different time slot.");
            }
            if(bookingStartTime.isEqual(existingStart) || bookingEndTime.isEqual(existingEnd)){
                throw new Exception("Slot not available choose different time slot.");
            }
        }
        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        return bookingRepository.findById(id).orElseThrow(() -> new Exception("Booking not found"));
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = getBookingsBySalon(salonId);

        if(date==null){
            return allBookings;
        }
        return allBookings.stream()
                .filter(booking -> isSameDate(booking.getStartTime(), date)||
                        isSameDate(booking.getEndTime(), date)).toList();
    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date){
        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);
        int totalEarnings = bookings.stream()
                .mapToInt(Booking::getTotalPrice)
                .sum();
        Integer totalBooking = bookings.size();
        List<Booking> cancelledBookings = bookings.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED)).toList();

        Double totalRefund = cancelledBookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        SalonReport report = new SalonReport();
        report.setSalonId(salonId);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalBookings(totalBooking);
        report.setTotalRefund(totalRefund);
        report.setTotalEarnings(totalEarnings);

        return report;
    }

    @Override
    public Booking bookingSucess(PaymentOrder order) throws Exception {
        Booking existingBooking = getBookingById(order.getBookingId());
        existingBooking.setStatus(BookingStatus.CONFIRM);
        return bookingRepository.save(existingBooking);
    }
}
