package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.client;

import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.config.PaymentConfig;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.CreateOrderRequest;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.CreateOrderResponse;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.TokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TelebirrClient {

    private final WebClient webClient;
    private final PaymentConfig config;

    public TelebirrClient(PaymentConfig config) {
        this.config = config;
        this.webClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .build();
    }

    public Mono<TokenResponse> getToken() {
        return webClient.post()
                .uri("/payment/v1/token")
                .header("Content-Type", "application/json")
                .header("X-APP-Key", config.getFabricAppId())
                .bodyValue(new TokenRequest(config.getAppSecret()))
                .retrieve()
                .bodyToMono(TokenResponse.class);
    }

    public Mono<CreateOrderResponse> createPreOrder(String token, CreateOrderRequest request) {
        return webClient.post()
                .uri("/payment/v1/merchant/preOrder")
                .header("Content-Type", "application/json")
                .header("X-APP-Key", config.getFabricAppId())
                .header("Authorization", token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CreateOrderResponse.class);
    }

    private static class TokenRequest {
        private String appSecret;

        public TokenRequest(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }
    }
}
