package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CallbackRequest {
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("trade_state")
    private String tradeState;
    @JsonProperty("total_amount")
    private String totalAmount;
    @JsonProperty("pay_time")
    private String payTime;
    
}
