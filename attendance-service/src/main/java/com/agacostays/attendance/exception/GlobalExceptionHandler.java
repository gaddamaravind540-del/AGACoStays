package com.agacostays.attendance.exception;
import com.agacostays.attendance.dto.response.ApiResponse;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiResponse<Void>> nf(ResourceNotFoundException e){return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));}
    @ExceptionHandler({BusinessRuleException.class,InvalidStatusException.class,DuplicateResourceException.class,InvalidCheckInLocationException.class})
    ResponseEntity<ApiResponse<Void>> br(RuntimeException e){return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));}
    @ExceptionHandler({AccessDeniedException.class,org.springframework.security.access.AccessDeniedException.class})
    ResponseEntity<ApiResponse<Void>> ad(RuntimeException e){return ResponseEntity.status(403).body(ApiResponse.error(e.getMessage()));}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> val(MethodArgumentNotValidException e){
        String msg=e.getBindingResult().getFieldErrors().stream().findFirst()
            .map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Validation failed");
        return ResponseEntity.badRequest().body(ApiResponse.error(msg));
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<Void>> generic(Exception e){return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));}
}
