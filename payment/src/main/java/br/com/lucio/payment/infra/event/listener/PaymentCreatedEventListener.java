package br.com.lucio.payment.infra.event.listener;

import br.com.lucio.payment.application.dto.OrderPaymentDTO;
import br.com.lucio.payment.application.service.PaymentRegister;
import br.com.lucio.payment.infra.config.EventsConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PaymentCreatedEventListener {

    private final PaymentRegister service;

    public PaymentCreatedEventListener(PaymentRegister service) {
        this.service = service;
    }

    @RabbitListener(queues = EventsConstants.QUEUE_EVENTS_PAYMENT_CREATED, containerFactory = "rabbitListenerContainerFactory")
    public void paymentCreatedEventListener(OrderPaymentDTO payment,
                                            Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("-----------> Received payment created: {}", payment);
        service.execute(payment);
        channel.basicAck(tag, false);
    }

}
