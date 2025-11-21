package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateOrderResponse {

    @JsonProperty("biz_content")
    private BizContent bizContent;

    private String code;
    private String message;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("sign_type")
    private String signType;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("nonce_str")
    private String nonceStr;

    @Data
    public static class BizContent {

        @JsonProperty("prepay_id")
        private String prepayId;
    }
}
