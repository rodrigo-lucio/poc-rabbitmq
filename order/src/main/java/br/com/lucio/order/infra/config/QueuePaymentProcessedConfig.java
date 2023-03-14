package br.com.lucio.order.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("amqpConfiguration")
public class QueuePaymentProcessedConfig {

    @Bean
    public FanoutExchange fanoutExchangePaymentEventsPaymentProcessed() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_PAYMENT_EVENTS_PAYMENT_PROCESSED).build();
    }
    @Bean
    public Queue queueEventPaymentEventsPaymentProcessedOrder() {
        return QueueBuilder.durable(EventsConstants.QUEUE_PAYMENT_EVENTS_PAYMENT_PROCESSED_ORDER).build();
    }

    @Bean
    public Binding bindingQueuePaymentProcessed() {
        return BindingBuilder.bind(queueEventPaymentEventsPaymentProcessedOrder())
                .to(fanoutExchangePaymentEventsPaymentProcessed());
    }

}
