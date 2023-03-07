package br.com.lucio.payment.application.service;

import br.com.lucio.payment.application.dto.PaymentGatewayDTO;
import br.com.lucio.payment.infra.repository.OrderPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentProcessor {

    private final OrderPaymentRepository orderPaymentRepository;

    private final ApplicationEventPublisher publisher;

    public PaymentProcessor(OrderPaymentRepository orderPaymentRepository, ModelMapper modelMapper, ApplicationEventPublisher publisher) {
        this.orderPaymentRepository = orderPaymentRepository;
        this.publisher = publisher;
    }

    @Transactional
    public void execute(UUID paymentId) {
        orderPaymentRepository.updateToProcessing(paymentId);
    }

    public void publishEvent(UUID paymentId) {
        publisher.publishEvent(new PaymentGatewayDTO(paymentId));
    }

}
