package br.com.lucio.order.infra.event.publisher;

import br.com.lucio.order.application.dto.OrderPaymentDTO;
import br.com.lucio.order.infra.config.EventsConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderPaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderPaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @EventListener
    public void publishEvent(OrderPaymentDTO payment) {
        log.info("-----------> Publishing event payment created {}", payment);
        rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_EVENTS_PAYMENT_CREATED, "", payment);
    }

}
