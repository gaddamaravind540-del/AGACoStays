package com.agacostays.gateway.security;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class PublicRouteValidator {

    public boolean isPublic(ServerHttpRequest request) {
        String path = request.getPath().value();
        HttpMethod method = request.getMethod();

        if (path.equals("/actuator/health")
                || path.startsWith("/actuator/health/")
                || path.equals("/api/gateway/health")) {
            return true;
        }

        // Authentication registration/login/refresh/OTP/password-reset flows are public.
        if (path.startsWith("/api/auth/")) {
            return isPublicAuthEndpoint(path, method);
        }

        // Public catalog routes are GET-only. Mutating operations under these
        // prefixes (e.g. booking creation) still require JWT validation.
        if (path.equals("/api/cities") || path.startsWith("/api/cities/")) {
            return HttpMethod.GET.equals(method);
        }

        if (path.equals("/api/hotel-branches") || path.startsWith("/api/hotel-branches/")) {
            return HttpMethod.GET.equals(method);
        }

        return false;
    }

    private boolean isPublicAuthEndpoint(String path, HttpMethod method) {
        if (!HttpMethod.POST.equals(method) && !HttpMethod.PUT.equals(method)) {
            return false;
        }

        return path.equals("/api/auth/register")
                || path.equals("/api/auth/login")
                || path.equals("/api/auth/refresh-token")
                || path.equals("/api/auth/forgot-password")
                || path.equals("/api/auth/reset-password")
                || path.equals("/api/auth/otp/send")
                || path.equals("/api/auth/otp/verify");
    }
}
