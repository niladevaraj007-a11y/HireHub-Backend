package com.Hirehub.repository;

import com.Hirehub.entity.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(
            Integer userId);

    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(
            Integer userId);

    long countByUserIdAndIsReadFalse(
            Integer userId);
}