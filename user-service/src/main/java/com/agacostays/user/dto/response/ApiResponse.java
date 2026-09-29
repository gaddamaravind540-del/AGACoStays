package com.agacostays.user.dto.response;
public record ApiResponse<T>(boolean success,String message,T data,String traceId) {
 public static <T> ApiResponse<T> success(String m,T d,String t){return new ApiResponse<>(true,m,d,t);}
 public static <T> ApiResponse<T> failure(String m,String t){return new ApiResponse<>(false,m,null,t);}
}