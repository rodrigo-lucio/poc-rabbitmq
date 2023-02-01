package br.com.lucio.person.infra.config.amqp;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventsConstants {

    public static final String EXCHANGE_EVENTS_PERSON_CRUD = "person.events.person-crud";
}
