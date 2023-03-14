package br.com.lucio.order.infra.event.listener;

import br.com.lucio.order.application.dto.PaymentProcessedDTO;
import br.com.lucio.order.application.service.OrderService;
import br.com.lucio.order.infra.config.EventsConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PaymentProcessedEventListener {

    private final OrderService service;

    public PaymentProcessedEventListener(OrderService service) {
        this.service = service;
    }

    @RabbitListener(queues = EventsConstants.QUEUE_PAYMENT_EVENTS_PAYMENT_PROCESSED_ORDER, containerFactory = "rabbitListenerContainerFactory")
    public void personCreatedEventListener(PaymentProcessedDTO paymentProcessed,
                                           Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("-----------> Received payment processed event: {}", paymentProcessed);
        this.service.updatePayment(paymentProcessed);
        channel.basicAck(tag, false);
    }

}
