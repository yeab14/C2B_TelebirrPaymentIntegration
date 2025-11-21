package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.util;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MiscUtil {

    private static final SecureRandom random = new SecureRandom();

   
    public static String createTimeStamp() {
        return DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }

    public static String createNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }

    public static String createMerchantOrderId() {
        return String.valueOf(System.currentTimeMillis());
    }
}
