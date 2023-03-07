package br.com.lucio.payment.infra.event.listener;

import br.com.lucio.payment.application.dto.PaymentProcessorDTO;
import br.com.lucio.payment.application.service.PaymentProcessor;
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
public class PaymentProcessorEventListenerYourself {

    private final PaymentProcessor service;

    public PaymentProcessorEventListenerYourself(PaymentProcessor service) {
        this.service = service;
    }

    @RabbitListener(queues = EventsConstants.QUEUE_PAYMENT_PROCESS_PAYMENT_YOURSELF, containerFactory = "rabbitListenerContainerFactory")
    public void execute(PaymentProcessorDTO payment,
                        Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("-----------> Received payment created to process: {}", payment);
        service.execute(payment.getId());
        service.publishEvent(payment.getId());
        channel.basicAck(tag, false);
    }

}
