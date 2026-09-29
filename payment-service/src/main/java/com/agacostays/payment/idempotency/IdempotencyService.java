package com.agacostays.payment.idempotency;

import com.agacostays.payment.entity.PaymentIdempotencyRecord;
import com.agacostays.payment.exception.IdempotencyConflictException;
import com.agacostays.payment.repository.PaymentRepository;
import com.agacostays.payment.entity.Payment;
import com.agacostays.payment.repository.PaymentIdempotencyRecordRepository;
import tools.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class IdempotencyService {
    private final PaymentIdempotencyRecordRepository repo;
    private final JsonMapper mapper;
    private final long ttlMinutes;
    public IdempotencyService(PaymentIdempotencyRecordRepository repo, JsonMapper mapper,
                              @Value("${payment.idempotency.ttl-minutes:1440}") long ttlMinutes){this.repo=repo;this.mapper=mapper;this.ttlMinutes=ttlMinutes;}
    public Optional<Payment> findExisting(String key, Object request, PaymentRepository payments){
        if(key==null || key.isBlank()) return Optional.empty();
        var rec=repo.findByIdempotencyKey(key).orElse(null);
        if(rec==null || rec.getExpiresAt().isBefore(Instant.now())) return Optional.empty();
        String hash=hash(request);
        if(!hash.equals(rec.getRequestHash())) throw new IdempotencyConflictException("Idempotency-Key was reused with a different request");
        return rec.getPaymentId()==null?Optional.empty():payments.findById(rec.getPaymentId());
    }
    public void save(String key,Object request,Long paymentId){
        if(key==null || key.isBlank()) return;
        repo.save(PaymentIdempotencyRecord.builder().idempotencyKey(key).requestHash(hash(request)).paymentId(paymentId)
                .createdAt(Instant.now()).expiresAt(Instant.now().plus(ttlMinutes, ChronoUnit.MINUTES)).build());
    }
    private String hash(Object value){ try { byte[] b=mapper.writeValueAsString(value).getBytes(StandardCharsets.UTF_8); return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(b)); } catch(Exception e){ throw new IllegalStateException("Cannot hash payment request",e);} }
}
