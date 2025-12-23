package com.yoho.service;

import com.yoho.model.Notification;
import com.yoho.payload.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    NotificationDTO createNotification(Notification notification) throws Exception;
    List<Notification> getAllNotificationsByUserId(Long userId);
    List<Notification> getAllNotificationsBySalonId(Long salonId);
    Notification markNotificationAsRead(Long notificationId) throws Exception;
    void deleteNotification(Long notificationId);
    List<Notification> getAllNotifications();
}
