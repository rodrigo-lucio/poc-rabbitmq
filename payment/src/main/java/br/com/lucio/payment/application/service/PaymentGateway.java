package br.com.lucio.payment.application.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface PaymentGateway {
    @Transactional
    void execute(UUID paymentId);

    void publishEvent(UUID id);
}
