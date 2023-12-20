package com.stock.stock.controller;

import com.stock.stock.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFound(
            NotFoundException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Map<String, String>> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            Map<String, String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());
            errors.add(error);
        });

        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.BAD_REQUEST, "validation error", errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @Getter
    public static final class ErrorResponse {
        private final HttpStatus status;
        private final String message;
        private final List<Map<String, String>> errors;

        public ErrorResponse(HttpStatus status, String message, List<Map<String, String>> errors) {
            this.status = status;
            this.message = message;
            this.errors = errors;
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageNotReadableResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "いずれかの入力形式が正しくありません。");

        MessageNotReadableResponse messageNotReadableResponse
                = new MessageNotReadableResponse(HttpStatus.BAD_REQUEST, "request error", error);
        return ResponseEntity.badRequest().body(messageNotReadableResponse);
    }

    @Getter
    public static final class MessageNotReadableResponse {
        private final HttpStatus status;
        private final String message;
        private final Map<String, String> error;

        public MessageNotReadableResponse(HttpStatus status, String message, Map<String, String> error) {
            this.status = status;
            this.message = message;
            this.error = error;
        }
    }

}


