package com.agacostays.notification.dto.response;

import java.time.Instant;

public record ApiResponse<T>(boolean success, String message, T data, String traceId, Instant timestamp) {
    public static <T> ApiResponse<T> ok(T data, String message, String traceId) {
        return new ApiResponse<>(true, message, data, traceId, Instant.now());
    }
    public static <T> ApiResponse<T> error(String message, String traceId) {
        return new ApiResponse<>(false, message, null, traceId, Instant.now());
    }
}
