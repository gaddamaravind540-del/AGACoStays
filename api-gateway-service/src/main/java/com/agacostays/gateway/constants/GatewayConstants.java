package com.agacostays.gateway.constants;

public final class GatewayConstants {
    public static final String DEFAULT_TRACE_ID_HEADER = "X-Trace-Id";
    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    public static final String USER_ID_HEADER = "X-User-Id";
    public static final String USERNAME_HEADER = "X-Username";
    public static final String ROLES_HEADER = "X-Roles";
    public static final String BRANCH_ID_HEADER = "X-Branch-Id";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private GatewayConstants() {}
}
