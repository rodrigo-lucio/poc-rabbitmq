package br.com.lucio.payment.infra.event.publisher;

import br.com.lucio.payment.application.dto.PaymentProcessorDTO;
import br.com.lucio.payment.infra.config.EventsConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentProcessorEventPublisherYourself {

    private final RabbitTemplate rabbitTemplate;

    public PaymentProcessorEventPublisherYourself(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @TransactionalEventListener
    public void paymentProcessorEventPublisher(PaymentProcessorDTO paymentProcessor) {
        log.info("-----------> Publish payment to processing in yourself: {}", paymentProcessor);
        this.rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_PAYMENT_YOURSELF, EventsConstants.QUEUE_PAYMENT_PROCESS_PAYMENT_YOURSELF, paymentProcessor);
    }

}
