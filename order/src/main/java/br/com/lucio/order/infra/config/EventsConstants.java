package br.com.lucio.order.infra.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventsConstants {

    public static final String EXCHANGE_EVENTS_PERSON_CREATED = "person.events.person-created";
    public static final String EXCHANGE_EVENTS_PERSON_UPDATED = "person.events.person-updated";
    public static final String EXCHANGE_EVENTS_PERSON_DELETED = "person.events.person-deleted";

    public static final String QUEUE_EVENTS_PERSON_CREATED = "person.events.person-created.order";
    public static final String QUEUE_EVENTS_PERSON_UPDATED = "person.events.person-updated.order";
    public static final String QUEUE_EVENTS_PERSON_DELETED = "person.events.person-deleted.order";


}
