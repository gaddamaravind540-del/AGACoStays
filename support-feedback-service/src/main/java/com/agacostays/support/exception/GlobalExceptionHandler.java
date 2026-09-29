package com.agacostays.support.exception;

import com.agacostays.support.dto.response.ApiResponse;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> validation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream().findFirst()
                .map(e -> e.getField()+": "+e.getDefaultMessage()).orElse("Validation failed");
        return ResponseEntity.badRequest().body(ApiResponse.success(msg, null));
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiResponse<String>> notFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(ApiResponse.success(ex.getMessage(), null));
    }
    @ExceptionHandler({BusinessRuleException.class,InvalidStatusException.class,DuplicateResourceException.class})
    ResponseEntity<ApiResponse<String>> conflict(RuntimeException ex) {
        return ResponseEntity.status(409).body(ApiResponse.success(ex.getMessage(), null));
    }
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ApiResponse<String>> denied(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(ApiResponse.success(ex.getMessage(), null));
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<String>> generic(Exception ex) {
        return ResponseEntity.status(500).body(ApiResponse.success("Internal support service error", null));
    }
}
