package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.service;

import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.client.TelebirrClient;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.config.PaymentConfig;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.CreateOrderRequest;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.CreateOrderResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {

    private final TokenService tokenService;
    private final TelebirrClient telebirrClient;
    private final PaymentConfig config;

    public PaymentService(TokenService tokenService, TelebirrClient telebirrClient, PaymentConfig config) {
        this.tokenService = tokenService;
        this.telebirrClient = telebirrClient;
        this.config = config;
    }

    public String createOrder(String title, String amount) {
        String token = tokenService.getToken();

        CreateOrderRequest request = buildCreateOrderRequest(title, amount);
        CreateOrderResponse response = telebirrClient.createPreOrder(token, request).block();

        if (response == null || response.getBizContent() == null) {
            throw new RuntimeException("Failed to create Telebirr preOrder");
        }

        String prepayId = response.getBizContent().getPrepayId();
        return buildPaymentUrl(prepayId);
    }

    private CreateOrderRequest buildCreateOrderRequest(String title, String amount) {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setTimestamp(Instant.now().toString());
        req.setNonceStr(UUID.randomUUID().toString().replace("-", ""));
        req.setMethod("payment.preorder");
        req.setVersion("1.0");

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
        biz.setPayeeIdentifier(config.getShortCode());
        biz.setPayeeIdentifierType("04");
        biz.setPayeeType("5000");
        biz.setRedirectUrl(config.getRedirectUrl());
        biz.setCallbackInfo("From Spring Boot service");

        req.setBizContent(biz);

  
        return req;
    }

    private String buildPaymentUrl(String prepayId) {
        return config.getBaseUrl() + "/pay?prepay_id=" + prepayId + "&version=1.0&trade_type=Checkout"; 
    }
}