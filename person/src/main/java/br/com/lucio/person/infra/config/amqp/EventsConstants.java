package br.com.lucio.person.infra.config.amqp;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventsConstants {

    public static final String EXCHANGE_EVENTS_PERSON_CREATED = "person.events.person-created";
    public static final String EXCHANGE_EVENTS_PERSON_UPDATED = "person.events.person-updated";
    public static final String EXCHANGE_EVENTS_PERSON_DELETED = "person.events.person-deleted";
}
