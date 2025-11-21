package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "telebirr")
@Data
public class PaymentConfig {
    private String baseUrl;
    private String fabricAppId;
    private String appSecret;
    private String merchantAppId;
    private String shortCode;
    private String notifyUrl;
    private String redirectUrl;

   
    private String privateKeyPem;
}
