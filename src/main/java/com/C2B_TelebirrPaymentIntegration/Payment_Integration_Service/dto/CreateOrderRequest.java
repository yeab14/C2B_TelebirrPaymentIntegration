package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private String timestamp;
    @JsonProperty("nonce_str")
    private String nonceStr;
    private String method;
    private String version;
    @JsonProperty("biz_content")
    private BizContent bizContent;
    private String sign;
    @JsonProperty("sign_type")
    private String signType;

    @Data
    public static class BizContent {
        @JsonProperty("notify_url")
        private String notifyUrl;
        private String appid;
        @JsonProperty("merch_code") 
        private String merchCode;
        @JsonProperty("merch_order_id")
        private String merchOrderId;
        @JsonProperty("trade_type")
        private String tradeType;
        private String title;
        @JsonProperty("total_amount")
        private String totalAmount;
        @JsonProperty("trans_currency")
        private String transCurrency;
        @JsonProperty("timeout_express")
        private String timeoutExpress;
        @JsonProperty("business_type")
        private String businessType;
        @JsonProperty("payee_identifier")
        private String payeeIdentifier;
        @JsonProperty("payee_identifier_type")
        private String payeeIdentifierType;
        @JsonProperty("payee_type")
        private String payeeType;
        @JsonProperty("redirect_url")
        private String redirectUrl;
        @JsonProperty("callback_info")
        private String callbackInfo;
    }
}