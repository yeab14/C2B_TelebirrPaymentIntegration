package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import java.math.BigDecimal;

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
        private String appid;
    
        @JsonProperty("business_type")
        private String businessType;
    
        @JsonProperty("merch_code")
        private String merchCode;
    
        @JsonProperty("merch_order_id")
        private String merchOrderId;
    
        @JsonProperty("notify_url")
        private String notifyUrl;
    
        @JsonProperty("redirect_url")
        private String redirectUrl;
    
        @JsonProperty("timeout_express")
        private String timeoutExpress;
    
        private String title;
    
        @JsonProperty("total_amount")
        private String totalAmount; 
    
        @JsonProperty("trade_type")
        private String tradeType;
    
        @JsonProperty("trans_currency")
        private String transCurrency;
    }
}
