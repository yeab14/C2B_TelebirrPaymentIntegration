package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateOrderResponse {
    @JsonProperty("biz_content")
    private BizContent bizContent;
    private String code;
    private String message;

    @Data
    public static class BizContent {
        @JsonProperty("prepay_id")
        private String prepayId;
       
    }
}
