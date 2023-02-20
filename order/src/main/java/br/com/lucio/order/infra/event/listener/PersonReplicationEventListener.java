package br.com.lucio.order.infra.event.listener;

import br.com.lucio.order.application.service.PersonService;
import br.com.lucio.order.infra.config.EventsConstants;
import br.com.lucio.order.infra.event.dto.PersonCrudEventDTO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PersonReplicationEventListener {

    private final PersonService service;

    public PersonReplicationEventListener(PersonService service) {
        this.service = service;
    }

    @RabbitListener(queues = EventsConstants.QUEUE_EVENTS_PERSON_CRUD, containerFactory = "rabbitListenerContainerFactory")
    public void personCreatedEventListener(PersonCrudEventDTO personCrudEventDTO,
                                           Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("-----------> Received crud event for person: {}", personCrudEventDTO);
        service.crudEvent(personCrudEventDTO);
        channel.basicAck(tag, false);
    }

}
