package com.agacostays.notification.provider;

public interface EmailProvider {
    ProviderResult send(String to, String subject, String htmlBody);
}
