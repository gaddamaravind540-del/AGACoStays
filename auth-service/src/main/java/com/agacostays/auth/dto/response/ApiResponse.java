package com.agacostays.auth.dto.response;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        String traceId
) {
    public static <T> ApiResponse<T> success(String message, T data, String traceId) {
        return new ApiResponse<>(true, message, data, traceId);
    }

    public static <T> ApiResponse<T> failure(String message, String traceId) {
        return new ApiResponse<>(false, message, null, traceId);
    }
}
