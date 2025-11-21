package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.CreateOrderInput;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.dto.CallbackRequest;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.service.CallbackService;
import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final CallbackService callbackService;

    public PaymentController(PaymentService paymentService, CallbackService callbackService) {
        this.paymentService = paymentService;
        this.callbackService = callbackService;
    }

    @PostMapping("/create")
public ResponseEntity<String> createOrder(@RequestBody CreateOrderInput input) {
    if (input.getTitle() == null || input.getAmount() == null)
        return ResponseEntity.badRequest().body("title and amount are required");

    try {
        String paymentUrl = paymentService.createOrder(input.getTitle(), input.getAmount());
        return ResponseEntity.ok(paymentUrl);
    } catch (Exception e) {
        e.printStackTrace(); 
        return ResponseEntity.status(500).body("Failed to create order: " + e.getMessage());
    }
}


    @PostMapping("/notify")
    public ResponseEntity<String> notifyCallback(@RequestBody CallbackRequest callbackRequest) {
        callbackService.processCallback(callbackRequest);
        return ResponseEntity.ok("Notification processed");
    }

    @GetMapping("/redirect")
    public ResponseEntity<String> redirect() {
        return ResponseEntity.ok("Redirect handled");
    }
}
