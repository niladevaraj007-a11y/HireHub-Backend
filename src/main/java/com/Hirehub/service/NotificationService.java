package com.Hirehub.service;

import com.Hirehub.entity.Notification;
import com.Hirehub.repository.NotificationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public NotificationService(
            NotificationRepository notificationRepository) {

        this.notificationRepository =
                notificationRepository;
    }

    // =========================================================
    // CREATE NOTIFICATION
    // =========================================================

    public Notification createNotification(
            Integer userId,
            String message) {

        Notification notification =
                new Notification();

        notification.setUserId(userId);

        notification.setMessage(message);

        notification.setCreatedAt(
                LocalDateTime.now());

        notification.setIsRead(false);

        return notificationRepository.save(
                notification);
    }

    // =========================================================
    // GET ALL NOTIFICATIONS FOR USER
    // =========================================================

    public List<Notification> getNotificationsByUser(
            Integer userId) {

        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(
                        userId);
    }

    // =========================================================
    // GET UNREAD NOTIFICATIONS
    // =========================================================

    public List<Notification> getUnreadNotifications(
            Integer userId) {

        return notificationRepository
                .findByUserIdAndIsReadFalseOrderByCreatedAtDesc(
                        userId);
    }

    // =========================================================
    // GET UNREAD NOTIFICATION COUNT
    // =========================================================

    public long getUnreadNotificationCount(
            Integer userId) {

        return notificationRepository
                .countByUserIdAndIsReadFalse(
                        userId);
    }

    // =========================================================
    // GET NOTIFICATION BY ID
    // =========================================================

    public Notification getNotificationById(
            Integer notificationId) {

        return notificationRepository
                .findById(notificationId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Notification not found"));
    }

    // =========================================================
    // MARK ONE NOTIFICATION AS READ
    // =========================================================

    public Notification markAsRead(
            Integer notificationId) {

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"));

        notification.setIsRead(true);

        return notificationRepository.save(
                notification);
    }

    // =========================================================
    // MARK ALL NOTIFICATIONS AS READ
    // =========================================================

    public void markAllAsRead(
            Integer userId) {

        List<Notification> notifications =
                notificationRepository
                        .findByUserIdOrderByCreatedAtDesc(
                                userId);

        for (Notification notification :
                notifications) {

            notification.setIsRead(true);
        }

        notificationRepository.saveAll(
                notifications);
    }

    // =========================================================
    // DELETE NOTIFICATION
    // =========================================================

    public void deleteNotification(
            Integer notificationId) {

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"));

        notificationRepository.delete(notification);
    }
}