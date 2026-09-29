package com.agacostays.auth.constants;

public final class KafkaTopicConstants {
    public static final String USER_REGISTERED = "auth.user-registered";
    public static final String LOGIN_SUCCESS = "auth.login-success";
    public static final String LOGIN_FAILED = "auth.login-failed";
    public static final String PASSWORD_RESET_REQUESTED = "auth.password-reset-requested";

    private KafkaTopicConstants() {}
}
