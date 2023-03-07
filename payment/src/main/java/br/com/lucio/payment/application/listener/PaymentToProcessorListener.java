package br.com.lucio.payment.application.listener;

import br.com.lucio.payment.application.dto.PaymentGatewayDTO;
import br.com.lucio.payment.infra.config.EventsConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentToProcessorListener {

    private final RabbitTemplate rabbitTemplate;

    public PaymentToProcessorListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @EventListener
    public void execute(PaymentGatewayDTO paymentProcessor) {
        log.info("-----------> Publish payment to processing: {}", paymentProcessor);
        rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_PAYMENT_YOURSELF, EventsConstants.QUEUE_PAYMENT_PROCESS_GATEWAY_YOURSELF, paymentProcessor);
    }
}
