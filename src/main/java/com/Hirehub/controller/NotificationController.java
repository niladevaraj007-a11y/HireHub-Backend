package com.Hirehub.controller;

import com.Hirehub.entity.Notification;
import com.Hirehub.service.NotificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
public class NotificationController {

    private final NotificationService notificationService;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    // =========================================================
    // CREATE NOTIFICATION
    // =========================================================
    //
    // POST
    // /api/notifications/create?userId=16&message=Hello
    //
    // =========================================================

    @PostMapping("/create")
    public ResponseEntity<Notification> createNotification(
            @RequestParam Integer userId,
            @RequestParam String message) {

        Notification notification =
                notificationService.createNotification(
                        userId,
                        message
                );

        return ResponseEntity.ok(notification);
    }

    // =========================================================
    // GET ALL NOTIFICATIONS FOR USER
    // =========================================================
    //
    // GET
    // /api/notifications/user/16
    //
    // =========================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(
            @PathVariable Integer userId) {

        List<Notification> notifications =
                notificationService.getNotificationsByUser(userId);

        return ResponseEntity.ok(notifications);
    }

    // =========================================================
    // GET UNREAD NOTIFICATIONS
    // =========================================================
    //
    // GET
    // /api/notifications/user/16/unread
    //
    // =========================================================

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(
            @PathVariable Integer userId) {

        List<Notification> notifications =
                notificationService.getUnreadNotifications(userId);

        return ResponseEntity.ok(notifications);
    }

    // =========================================================
    // GET UNREAD NOTIFICATION COUNT
    // =========================================================
    //
    // GET
    // /api/notifications/user/16/count
    //
    // =========================================================

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getUnreadNotificationCount(
            @PathVariable Integer userId) {

        long count =
                notificationService.getUnreadNotificationCount(userId);

        return ResponseEntity.ok(count);
    }

    // =========================================================
    // GET NOTIFICATION BY ID
    // =========================================================
    //
    // GET
    // /api/notifications/48
    //
    // =========================================================

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(
            @PathVariable Integer notificationId) {

        Notification notification =
                notificationService.getNotificationById(notificationId);

        return ResponseEntity.ok(notification);
    }

    // =========================================================
    // MARK ONE NOTIFICATION AS READ
    // =========================================================
    //
    // PUT
    // /api/notifications/48/read
    //
    // =========================================================

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markAsRead(
            @PathVariable Integer notificationId) {

        Notification notification =
                notificationService.markAsRead(notificationId);

        return ResponseEntity.ok(notification);
    }

    // =========================================================
    // MARK ALL NOTIFICATIONS AS READ
    // =========================================================
    //
    // PUT
    // /api/notifications/user/16/read-all
    //
    // =========================================================

    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<String> markAllAsRead(
            @PathVariable Integer userId) {

        notificationService.markAllAsRead(userId);

        return ResponseEntity.ok(
                "All notifications marked as read"
        );
    }

    // =========================================================
    // DELETE NOTIFICATION
    // =========================================================
    //
    // DELETE
    // /api/notifications/48
    //
    // =========================================================

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Integer notificationId) {

        notificationService.deleteNotification(notificationId);

        return ResponseEntity.ok(
                "Notification deleted successfully"
        );
    }
}