package com.salon.user.controller;

import com.salon.user.mapper.NotificationMapper;
import com.salon.user.entity.Notification;
import com.salon.user.payload.dto.BookingDTO;
import com.salon.user.payload.dto.NotificationDTO;
import com.salon.user.service.NotificationService;
import com.salon.user.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications/salon-owner")
public class SalonNotificationController {

    private final NotificationService notificationService;
    private final BookingFeignClient bookingFeignClient;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationBySalonId(
            @PathVariable Long salonId
    ){
        List<Notification> notifications = notificationService.getAllNotificationsBySalonId(salonId);
        List<NotificationDTO> notificationDTOS = notifications.stream().map(
                (notification -> {
                    BookingDTO bookingDTO = null;
                    try{
                        bookingDTO = bookingFeignClient.getBookingById(
                                notification.getBookingId()).getBody();
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                    return NotificationMapper.toDTO(notification,bookingDTO);
                })).collect(Collectors.toList());
        return ResponseEntity.ok(notificationDTOS);
    }
}
