package com.agacostays.payment.util;
import java.math.BigDecimal; import java.math.RoundingMode; import java.nio.charset.StandardCharsets; import java.security.MessageDigest; import java.util.HexFormat;
public final class PaymentUtil { private PaymentUtil(){} public static BigDecimal normalize(BigDecimal v){return v.setScale(2,RoundingMode.HALF_UP);} public static String sha256(String s){try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8)));}catch(Exception e){throw new IllegalStateException(e);}} }
