package com.agacostays.notification.provider;

public interface SmsProvider {
    ProviderResult send(String phone, String message);
}
