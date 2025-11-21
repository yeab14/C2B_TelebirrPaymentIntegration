package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenResponse {
    @JsonProperty("token")
    private String token;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("error")
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;
}