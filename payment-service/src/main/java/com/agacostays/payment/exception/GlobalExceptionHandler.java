package com.agacostays.payment.exception;

import com.agacostays.payment.dto.response.ApiResponse;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<ApiResponse<Void>> notFound(ResourceNotFoundException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(e.getMessage()));}
 @ExceptionHandler({AccessDeniedException.class,org.springframework.security.access.AccessDeniedException.class}) ResponseEntity<ApiResponse<Void>> denied(Exception e){return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.fail(e.getMessage()));}
 @ExceptionHandler({BusinessRuleException.class,InvalidStatusException.class,DuplicateResourceException.class,IdempotencyConflictException.class,IllegalArgumentException.class}) ResponseEntity<ApiResponse<Void>> bad(RuntimeException e){return ResponseEntity.badRequest().body(ApiResponse.fail(e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiResponse<Void>> validation(MethodArgumentNotValidException e){String m=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining("; ")); return ResponseEntity.badRequest().body(ApiResponse.fail(m));}
 @ExceptionHandler(Exception.class) ResponseEntity<ApiResponse<Void>> generic(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail("Unexpected server error"));}
}
