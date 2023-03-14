package br.com.lucio.order.infra.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventsConstants {

    //Listeners
    public static final String EXCHANGE_EVENTS_PERSON_CRUD = "person.events.person-crud";
    public static final String QUEUE_EVENTS_PERSON_CRUD = "person.events.person-crud.order";
    public static final String EXCHANGE_PAYMENT_EVENTS_PAYMENT_PROCESSED = "payment.events.payment-processed";
    public static final String QUEUE_PAYMENT_EVENTS_PAYMENT_PROCESSED_ORDER = "payment.events.payment-processed.order";

    //Publishers
    public static final String EXCHANGE_EVENTS_PAYMENT_CREATED = "order.events.payment-created";
    public static final String QUEUE_EVENTS_PAYMENT_CREATED = "order.events.payment-created.payment";
}
