package com.agacostays.notification.exception;

import com.agacostays.notification.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private String trace(HttpServletRequest r){return r.getHeader("X-Trace-Id");}
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiResponse<Void>> notFound(ResourceNotFoundException e, HttpServletRequest r){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage(),trace(r)));}
    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    ResponseEntity<ApiResponse<Void>> denied(Exception e, HttpServletRequest r){return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(e.getMessage(),trace(r)));}
    @ExceptionHandler({BusinessRuleException.class, DuplicateResourceException.class, InvalidStatusException.class})
    ResponseEntity<ApiResponse<Void>> badBusiness(RuntimeException e, HttpServletRequest r){return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage(),trace(r)));}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> validation(MethodArgumentNotValidException e, HttpServletRequest r){
        var msg=e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Validation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(msg,trace(r)));
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<Void>> generic(Exception e, HttpServletRequest r){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Unexpected error",trace(r)));}
}
