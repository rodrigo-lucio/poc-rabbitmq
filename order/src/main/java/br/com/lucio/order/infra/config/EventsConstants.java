package br.com.lucio.order.infra.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventsConstants {

    //Listeners
    public static final String EXCHANGE_EVENTS_PERSON_CRUD = "person.events.person-crud";
    public static final String QUEUE_EVENTS_PERSON_CRUD = "person.events.person-crud.order";

    //Publishers
    public static final String EXCHANGE_EVENTS_PAYMENT_CREATED = "order.payment.events.payment-created";


}
