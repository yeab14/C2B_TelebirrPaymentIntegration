package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;

import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.client.TelebirrClient;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.TokenResponse;

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

            if (response == null)
                throw new RuntimeException("No response received from Telebirr token endpoint");

            if (response.getError() != null)
                throw new RuntimeException(response.getErrorDescription());

            if (response.getToken() == null)
                throw new RuntimeException("Token is null in Telebirr response");

            Long expiresIn = response.getExpiresIn();
            if (expiresIn == null || expiresIn <= 0)
                expiresIn = 300L;

            cachedToken.set(response.getToken());
            tokenExpiry = Instant.now().plusSeconds(expiresIn - 60);
        }
        return cachedToken.get();
    }
}
