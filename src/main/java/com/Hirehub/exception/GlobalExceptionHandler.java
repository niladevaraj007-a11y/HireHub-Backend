package com.Hirehub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(
            RuntimeException ex) {

        String message = ex.getMessage();

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if ("Job not found".equalsIgnoreCase(message)) {

            status = HttpStatus.NOT_FOUND;

        } else if (
                "You have already applied for this job"
                        .equalsIgnoreCase(message)) {

            status = HttpStatus.CONFLICT;

        }

        Map<String, Object> response =
                new LinkedHashMap<>();

        response.put(
                "timestamp",
                LocalDateTime.now()
        );

        response.put(
                "status",
                status.value()
        );

        response.put(
                "error",
                status.getReasonPhrase()
        );

        response.put(
                "message",
                message
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
