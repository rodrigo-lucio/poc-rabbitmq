package br.com.lucio.person.infra.event.publisher;

import br.com.lucio.person.infra.config.amqp.EventsConstants;
import br.com.lucio.person.infra.event.dto.PersonCrudEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonReplicationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public PersonReplicationEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @EventListener
    public void publishCrudEvent(PersonCrudEventDTO personEvent){
        log.info("-----------> Publishing event person created {}", personEvent);
        rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_EVENTS_PERSON_CRUD, "", personEvent);
    }


}
