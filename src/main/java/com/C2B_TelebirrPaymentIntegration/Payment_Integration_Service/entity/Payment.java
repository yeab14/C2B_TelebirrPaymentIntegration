package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "merchant_order_id", unique = true, nullable = false)
    private String merchantOrderId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "amount", nullable = false)
    private String amount;

    @Column(name = "payment_time")
    private Instant paymentTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "callback_info")
    private String callbackInfo;

    @Column(name = "transaction_id")
    private String transactionId;

   
}
