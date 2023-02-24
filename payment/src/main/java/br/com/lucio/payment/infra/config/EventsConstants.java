package br.com.lucio.payment.infra.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventsConstants {

    public static final String QUEUE_EVENTS_PAYMENT_CREATED = "order.payment.payment-created.payment";
    public static final String EXCHANGE_EVENTS_PAYMENT_CREATED = "order.payment.events.payment-created";
    public static final String EXCHANGE_PAYMENT_YOURSELF = "payment.yourself";
    public static final String QUEUE_PAYMENT_PROCESSOR_YOURSELF = "payment.process-payment.yourself";
    public static final String EXCHANGE_PAYMENT_EVENTS_PAYMENT_PROCESSED = "payment.events.payment-processed";
    public static final String QUEUE_PAYMENT_EVENTS_PAYMENT_PROCESSED_ORDER = "payment.events.payment-processed.order";
}
