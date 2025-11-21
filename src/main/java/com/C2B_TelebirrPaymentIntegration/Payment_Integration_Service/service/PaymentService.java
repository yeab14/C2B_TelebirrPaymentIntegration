package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.service;

// import java.math.BigDecimal;
import java.security.PrivateKey;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.client.TelebirrClient;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.config.PaymentConfig;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.CreateOrderRequest;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.CreateOrderResponse;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.util.SignUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentService {

    private final TokenService tokenService;
    private final TelebirrClient telebirrClient;
    private final PaymentConfig config;
    private final PrivateKey privateKey;

    public PaymentService(TokenService tokenService, TelebirrClient telebirrClient, PaymentConfig config) throws Exception {
        this.tokenService = tokenService;
        this.telebirrClient = telebirrClient;
        this.config = config;

        // Load private key for signing
        this.privateKey = SignUtil.loadPrivateKey(config.getPrivateKeyPem());
    }

    public String createOrder(String title, String amount) throws Exception {
        String token = tokenService.getToken();

        CreateOrderRequest request = buildCreateOrderRequest(title, amount);

        // Prepare biz_content JSON for signing
        ObjectMapper mapper = new ObjectMapper();
        String bizContentJson = mapper.writeValueAsString(request.getBizContent());

        // Map for signing
        Map<String, Object> signParams = Map.of(
                "timestamp", request.getTimestamp(),
                "nonce_str", request.getNonceStr(),
                "method", request.getMethod(),
                "version", request.getVersion(),
                "biz_content", mapper.readValue(bizContentJson, Object.class)
        );

        // Generate signature
        request.setSign(SignUtil.generateSign(signParams, privateKey));

        // Log request for debugging
        System.out.println(">>> Telebirr CreateOrder Request: " + mapper.writeValueAsString(request));

        // Call Telebirr API
        CreateOrderResponse response = telebirrClient.createPreOrder(token, request).block();
        if (response == null || response.getBizContent() == null) {
            throw new RuntimeException("Failed to create Telebirr preOrder");
        }

        String prepayId = response.getBizContent().getPrepayId();
        return buildPaymentUrl(prepayId);
    }

    private CreateOrderRequest buildCreateOrderRequest(String title, String amount) {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setTimestamp(String.valueOf(Instant.now().getEpochSecond())); // seconds
        req.setNonceStr(UUID.randomUUID().toString().replace("-", ""));
        req.setMethod("payment.preorder");
        req.setVersion("1.0");
        req.setSignType("SHA256WithRSA");

        CreateOrderRequest.BizContent biz = new CreateOrderRequest.BizContent();
        biz.setNotifyUrl(config.getNotifyUrl());
        biz.setAppid(config.getMerchantAppId());
        biz.setMerchCode(config.getShortCode());
        biz.setMerchOrderId(String.valueOf(System.currentTimeMillis()));
        biz.setTradeType("Checkout");
        biz.setTitle(title);
        biz.setTotalAmount(amount);
        biz.setTransCurrency("ETB");
        biz.setTimeoutExpress("120m");
        biz.setBusinessType("BuyGoods");
        // biz.setPayeeIdentifier(config.getShortCode());
        // biz.setPayeeIdentifierType("04");
        // biz.setPayeeType("5000");
        biz.setRedirectUrl(config.getRedirectUrl());
        // biz.setCallbackInfo("From Spring Boot service");

        req.setBizContent(biz);
        return req;
    }

    private String buildPaymentUrl(String prepayId) {
        return config.getBaseUrl()
                + "/pay?prepay_id=" + prepayId
                + "&version=1.0"
                + "&trade_type=Checkout";
    }
}
