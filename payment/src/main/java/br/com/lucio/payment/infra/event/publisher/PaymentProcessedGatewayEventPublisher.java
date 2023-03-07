package br.com.lucio.payment.infra.event.publisher;

import br.com.lucio.payment.application.dto.PaymentProcessedDTO;
import br.com.lucio.payment.infra.config.EventsConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentProcessedGatewayEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public PaymentProcessedGatewayEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @EventListener
    public void paymentProcessorEventPublisher(PaymentProcessedDTO paymentProcessedDTO) {
        log.info("-----------> Publish payment processed: {}", paymentProcessedDTO);
        this.rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_PAYMENT_EVENTS_PAYMENT_PROCESSED, "", paymentProcessedDTO);
    }

}
