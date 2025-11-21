package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.service;

import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.client.TelebirrClient;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.TokenResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TokenService {

    private final TelebirrClient telebirrClient;
    private final AtomicReference<String> cachedToken = new AtomicReference<>();
    private Instant tokenExpiry = Instant.EPOCH;

    public TokenService(TelebirrClient telebirrClient) {
        this.telebirrClient = telebirrClient;
    }

    public synchronized String getToken() {
        if (cachedToken.get() == null || Instant.now().isAfter(tokenExpiry)) {
            TokenResponse response = telebirrClient.getToken().block();
            if (response != null && response.getToken() != null) {
                cachedToken.set(response.getToken());
                tokenExpiry = Instant.now().plusSeconds(response.getExpiresIn() - 60); 
            } else {
                throw new RuntimeException("Failed to obtain Telebirr token");
            }
        }
        return cachedToken.get();
    }
}
