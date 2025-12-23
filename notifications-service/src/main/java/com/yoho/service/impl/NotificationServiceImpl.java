package com.yoho.service.impl;

import com.yoho.mapper.NotificationMapper;
import com.yoho.model.Notification;
import com.yoho.payload.dto.BookingDTO;
import com.yoho.payload.dto.NotificationDTO;
import com.yoho.repository.NotificationRepository;
import com.yoho.service.NotificationService;
import com.yoho.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookingFeignClient bookingFeignClient;

    @Override
    public NotificationDTO createNotification(Notification notification) throws Exception {
        Notification saveNotification = notificationRepository.save(notification);

        BookingDTO bookingDTO = bookingFeignClient.getBookingById(saveNotification.getBookingId()).getBody();

        NotificationDTO notificationDTO = NotificationMapper.toDTO(saveNotification, bookingDTO);

        return notificationDTO;
    }

    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getAllNotificationsBySalonId(Long salonId) {
        return notificationRepository.findBySalonId(salonId);
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) throws Exception {
        return notificationRepository.findById(notificationId).map(
                notification -> {
                    notification.setIsRead(true);
                    return notificationRepository.save(notification);
                }
        ).orElseThrow(() -> new Exception("Notification not found"));
    }

    @Override
    public void deleteNotification(Long notificationId) {

    }

    @Override
    public List<Notification> getAllNotifications() {
        return List.of();
    }
}
