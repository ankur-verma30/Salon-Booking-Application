package com.salon.booking.controller;

import com.salon.booking.domain.BookingStatus;
import com.salon.booking.domain.PaymentMethod;
import com.salon.booking.dto.*;
import com.salon.booking.mapper.BookingMapper;
import com.salon.booking.entity.Booking;
import com.salon.booking.entity.SalonReport;
import com.salon.booking.service.BookingService;
import com.salon.booking.service.client.PaymentFeignClient;
import com.salon.booking.service.client.SalonFeignClient;
import com.salon.booking.service.client.ServiceOfferingFeignClient;
import com.salon.booking.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final SalonFeignClient salonFeignClient;
    private final UserFeignClient userFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createBooking(@RequestParam Long salonId,
                                                 @RequestParam PaymentMethod paymentMethod,
                                                 @RequestBody BookingRequest bookingRequest,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();


        Set<ServiceDTO> serviceDTOSet = serviceOfferingFeignClient.getServicesByIds(bookingRequest.getServiceIds()).getBody();

        if(serviceDTOSet.isEmpty()){
            throw new Exception("Service not found");
        }

        Booking booking = bookingService.createBooking(bookingRequest, user, salon, serviceDTOSet);
        BookingDTO bookingDTO = BookingMapper.toDTO(booking);


        PaymentLinkResponse res = paymentFeignClient.createPaymentLink(bookingDTO, paymentMethod, jwt).getBody();

        return ResponseEntity.ok(res);

    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        if(user == null || user.getId() == null){
            throw new Exception("User not found from jwt token...");
        }
        List<Booking> bookings = bookingService.getBookingsByCustomer(user.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();
        List<Booking> bookings = bookingService.getBookingsBySalon(salonDTO.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) throws Exception {

        Booking booking = bookingService.updateBooking(bookingId,status);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(@PathVariable Long salonId, @RequestParam(required = false) LocalDate date){
        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);
        List<BookingSlotDTO> slotDTOS = bookings.stream()
                .map(booking -> {
                    BookingSlotDTO slotDTO = new BookingSlotDTO();
                    slotDTO.setStartTime(booking.getStartTime());
                    slotDTO.setEndTime(booking.getEndTime());
                    return slotDTO;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(slotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();
        SalonReport report = bookingService.getSalonReport(salonDTO.getId());
        return ResponseEntity.ok(report);

    }


}
