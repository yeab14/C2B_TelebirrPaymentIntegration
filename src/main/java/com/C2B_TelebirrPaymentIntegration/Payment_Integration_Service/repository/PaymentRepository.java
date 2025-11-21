package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.repository;

import com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByMerchantOrderId(String merchantOrderId);
}
