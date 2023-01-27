package br.com.lucio.person.infra.event;

import br.com.lucio.person.infra.config.amqp.EventsConstants;
import br.com.lucio.person.infra.event.dto.PersonCreatedDTO;
import br.com.lucio.person.infra.event.dto.PersonDeletedDTO;
import br.com.lucio.person.infra.event.dto.PersonUpdatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonReplicationEventListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @EventListener
    public void publishPersonCreated(PersonCreatedDTO personCreated){
        log.info("-----------> Publishing event person created {}", personCreated.getPerson());
        rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_EVENTS_PERSON_CREATED, "", personCreated);
    }

    @Async
    @EventListener
    public void publishPersonUpdated(PersonUpdatedDTO personUpdated){
        log.info("-----------> Publishing event person updated {}", personUpdated.getPerson());
        rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_EVENTS_PERSON_UPDATED, "", personUpdated);
    }

    @Async
    @EventListener
    public void publicPersonDeleted(PersonDeletedDTO personDeleted){
        log.info("-----------> Publishing event person deleted {}", personDeleted.getId());
        rabbitTemplate.convertAndSend(EventsConstants.EXCHANGE_EVENTS_PERSON_UPDATED, "", personDeleted);
    }

}
