package com.agacostays.notification.provider.impl;

import com.agacostays.notification.provider.EmailProvider;
import com.agacostays.notification.provider.ProviderResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailProvider implements EmailProvider {
    private final JavaMailSender sender;
    private final boolean enabled;
    private final String from;
    public SmtpEmailProvider(JavaMailSender sender, @Value("${notification.email.enabled:false}") boolean enabled, @Value("${notification.email.from:no-reply@agacostays.com}") String from) {
        this.sender=sender; this.enabled=enabled; this.from=from;
    }
    @Override public ProviderResult send(String to, String subject, String body) {
        if (!enabled) return new ProviderResult(false, "SMTP email provider is disabled");
        try {
            var msg = new SimpleMailMessage();
            msg.setFrom(from); msg.setTo(to); msg.setSubject(subject); msg.setText(body); sender.send(msg);
            return new ProviderResult(true, "Email sent through SMTP");
        } catch (Exception ex) { return new ProviderResult(false, ex.getMessage()); }
    }
}
