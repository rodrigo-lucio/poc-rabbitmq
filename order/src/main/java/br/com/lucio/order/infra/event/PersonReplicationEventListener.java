package br.com.lucio.order.infra.event;

import br.com.lucio.order.application.service.PersonService;
import br.com.lucio.order.infra.config.EventsConstants;
import br.com.lucio.order.infra.event.dto.PersonCreatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonReplicationEventListener {

    @Autowired
    private PersonService service;

    @RabbitListener(queues = EventsConstants.QUEUE_EVENTS_PERSON_CREATED)
    public void pedidoCriadoEvent(PersonCreatedDTO personCreatedDTO) {
        log.info("-----------> Received event person created: {}", personCreatedDTO.getPerson());
        service.create(personCreatedDTO.getPerson());
    }

}
