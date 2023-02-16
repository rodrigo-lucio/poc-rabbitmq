package br.com.lucio.order.infra.event.publisher;

import br.com.lucio.order.application.dto.OrderPaymentDTO;
import br.com.lucio.order.infra.config.EventsConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OrderPaymentEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @EventListener
    public void publishEvent(List<OrderPaymentDTO> paymentsCreated){
        paymentsCreated.forEach(payment -> {
            log.info("-----------> Publishing event payment created {}", payment);
            rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_EVENTS_PAYMENT_CREATED, "", payment);
        });
    }

}
