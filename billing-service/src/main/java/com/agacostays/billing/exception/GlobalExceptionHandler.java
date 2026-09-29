package com.agacostays.billing.exception;
import com.agacostays.billing.dto.response.ApiResponse;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import org.springframework.web.bind.MethodArgumentNotValidException;
@RestControllerAdvice public class GlobalExceptionHandler{
 @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<ApiResponse<Void>> nf(ResourceNotFoundException e){return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));}
 @ExceptionHandler({BusinessRuleException.class,InvalidStatusException.class}) ResponseEntity<ApiResponse<Void>> br(RuntimeException e){return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiResponse<Void>> v(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(ApiResponse.error("Validation failed"));}
 @ExceptionHandler(Exception.class) ResponseEntity<ApiResponse<Void>> x(Exception e){return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));}
}
