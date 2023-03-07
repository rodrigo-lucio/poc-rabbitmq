package br.com.lucio.payment.application.service.impl;

import br.com.lucio.payment.application.dto.PaymentProcessedDTO;
import br.com.lucio.payment.application.service.PaymentGateway;
import br.com.lucio.payment.domain.entity.PaymentStatus;
import br.com.lucio.payment.infra.repository.OrderPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentGatewayPagarme implements PaymentGateway {

    private final OrderPaymentRepository orderPaymentRepository;

    private final ApplicationEventPublisher publisher;

    public PaymentGatewayPagarme(OrderPaymentRepository orderPaymentRepository, ModelMapper modelMapper, ApplicationEventPublisher publisher) {
        this.orderPaymentRepository = orderPaymentRepository;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public void execute(UUID paymentId) {
        sendToGatewayAndProcess(paymentId);
    }

    private void sendToGatewayAndProcess(UUID paymentId) {
        Random random = new Random();
        PaymentStatus status;
        // Simulaçao dos status retornados do gateway
        int range = random.nextInt(1, 10);

        if (range >= 3) {
            sleep(50);
            orderPaymentRepository.updateStatusAndDateConfirmed(PaymentStatus.CONFIRMED, LocalDateTime.now(), LocalDateTime.now(), paymentId);
        } else if (range == 2) {
            orderPaymentRepository.updateToUnauthorized(paymentId);
        } else if (range == 1) {
            orderPaymentRepository.updateToErro(paymentId);
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publishEvent(UUID paymentId) {
        orderPaymentRepository.findById(paymentId).ifPresent(payment -> {
            publisher.publishEvent(new PaymentProcessedDTO(payment.getId(), payment.getStatus(), payment.getDateConfirmed()));
        });
    }

}
