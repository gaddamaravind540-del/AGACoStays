package com.agacostays.booking.idempotency;

import com.agacostays.booking.exception.DuplicateBookingException;
import com.agacostays.booking.repository.IdempotencyRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class IdempotencyService {

    private final IdempotencyRecordRepository repository;

    public IdempotencyService(IdempotencyRecordRepository repository) {
        this.repository = repository;
    }

    public void validateAndStore(String key, String requestPayload) {
        if (key == null || key.isBlank()) return;

        String hash = sha256(requestPayload);
        var existing = repository.findByIdempotencyKey(key);
        if (existing.isPresent()) {
            if (!existing.get().getRequestHash().equals(hash)) {
                throw new DuplicateBookingException("Idempotency key was already used with different request data");
            }
            throw new DuplicateBookingException("Request already processed for Idempotency-Key: " + key);
        }

        repository.save(IdempotencyRecord.builder()
                .idempotencyKey(key)
                .requestHash(hash)
                .build());
    }

    private String sha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return java.util.HexFormat.of().formatHex(md.digest(text.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to hash request", e);
        }
    }
}
