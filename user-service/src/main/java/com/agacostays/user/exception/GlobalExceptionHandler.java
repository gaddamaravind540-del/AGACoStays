package com.agacostays.user.exception;
import com.agacostays.user.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({DuplicateUserException.class,DuplicateRoleException.class,DuplicatePermissionException.class})
    ResponseEntity<ApiResponse<Void>> conflict(RuntimeException e,HttpServletRequest r){return body(HttpStatus.CONFLICT,e.getMessage(),r);}
    @ExceptionHandler({UserNotFoundException.class,RoleNotFoundException.class,PermissionNotFoundException.class,StaffNotFoundException.class,CustomerNotFoundException.class,ManagerNotFoundException.class,RootAdminNotFoundException.class})
    ResponseEntity<ApiResponse<Void>> notFound(RuntimeException e,HttpServletRequest r){return body(HttpStatus.NOT_FOUND,e.getMessage(),r);}
    @ExceptionHandler({PermissionDeniedException.class})
    ResponseEntity<ApiResponse<Void>> forbidden(RuntimeException e,HttpServletRequest r){return body(HttpStatus.FORBIDDEN,e.getMessage(),r);}
    @ExceptionHandler({BranchMappingException.class,InvalidRoleException.class,InvalidUserStatusException.class,InvalidStaffTransferException.class})
    ResponseEntity<ApiResponse<Void>> badRequest(RuntimeException e,HttpServletRequest r){return body(HttpStatus.BAD_REQUEST,e.getMessage(),r);}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> validation(MethodArgumentNotValidException e,HttpServletRequest r){
        String m=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining(", "));
        return body(HttpStatus.BAD_REQUEST,m,r);
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<Void>> generic(Exception e,HttpServletRequest r){return body(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected user-service error",r);}
    private ResponseEntity<ApiResponse<Void>> body(HttpStatus s,String m,HttpServletRequest r){
        String t=r.getHeader("X-Trace-Id");
        return ResponseEntity.status(s).body(ApiResponse.failure(m,t==null?"unknown":t));
    }
}
