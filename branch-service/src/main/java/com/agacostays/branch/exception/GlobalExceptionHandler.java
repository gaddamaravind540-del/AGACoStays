package com.agacostays.branch.exception;
import com.agacostays.branch.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler({CityNotFoundException.class,BranchNotFoundException.class,BranchPhotoNotFoundException.class,ReceptionistContactNotFoundException.class,EmergencyContactNotFoundException.class})
 ResponseEntity<ApiResponse<Void>> notFound(RuntimeException e,HttpServletRequest r){return body(HttpStatus.NOT_FOUND,e.getMessage(),r);}
 @ExceptionHandler({DuplicateCityException.class,DuplicateBranchException.class})
 ResponseEntity<ApiResponse<Void>> conflict(RuntimeException e,HttpServletRequest r){return body(HttpStatus.CONFLICT,e.getMessage(),r);}
 @ExceptionHandler({InvalidLocationException.class,InvalidBranchStatusException.class,InvalidContactStatusException.class,BranchPhotoException.class,FileUploadException.class,StorageException.class})
 ResponseEntity<ApiResponse<Void>> bad(RuntimeException e,HttpServletRequest r){return body(HttpStatus.BAD_REQUEST,e.getMessage(),r);}
 @ExceptionHandler(BranchAccessDeniedException.class)
 ResponseEntity<ApiResponse<Void>> denied(RuntimeException e,HttpServletRequest r){return body(HttpStatus.FORBIDDEN,e.getMessage(),r);}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<ApiResponse<Void>> validation(MethodArgumentNotValidException e,HttpServletRequest r){
  String m=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining(", "));
  return body(HttpStatus.BAD_REQUEST,m,r);
 }
 @ExceptionHandler(Exception.class)
 ResponseEntity<ApiResponse<Void>> generic(Exception e,HttpServletRequest r){return body(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected branch-service error",r);}
 private ResponseEntity<ApiResponse<Void>> body(HttpStatus s,String m,HttpServletRequest r){
  return ResponseEntity.status(s).body(ApiResponse.failure(m,java.util.Optional.ofNullable(r.getHeader("X-Trace-Id")).orElse("unknown")));
 }
}
