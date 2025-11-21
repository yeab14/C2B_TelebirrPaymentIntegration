package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.service;

import org.springframework.stereotype.Service;

import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.CallbackRequest;

@Service
public class CallbackService {

    public void processCallback(CallbackRequest callbackRequest) {
        // Validate callback signature (use SignatureUtil)
        // Update payment status in DB using PaymentRepository
        // Log and handle business logic
    }
}