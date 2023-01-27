package br.com.lucio.order.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

@Configuration
@DependsOn("amqpConfiguration")
public class QueuePersonReplicationConfig {

    @Bean
    public FanoutExchange fanoutExchangePersonCreated() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PERSON_CREATED).build();
    }
    @Bean
    public Queue queueEventsPersonCreated() {
        return QueueBuilder.durable(EventsConstants.QUEUE_EVENTS_PERSON_CREATED).build();
    }

    @Bean
    public Binding bindingQueueEventsPersonCreated() {
        return BindingBuilder.bind(queueEventsPersonCreated()).to(fanoutExchangePersonCreated());
    }

    @Bean
    public FanoutExchange fanoutExchangePersonUpdated() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PERSON_UPDATED).build();
    }

    @Bean
    public Queue queueEventsPersonUpdated() {
        return QueueBuilder.durable(EventsConstants.QUEUE_EVENTS_PERSON_UPDATED).build();
    }

    @Bean
    public Binding bindingQueueEventsPersonUpdated() {
        return BindingBuilder.bind(queueEventsPersonUpdated()).to(fanoutExchangePersonUpdated());
    }

    @Bean
    public FanoutExchange fanoutExchangePersonDeleted() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PERSON_DELETED).build();
    }

    @Bean
    public Queue queueEventsPersonDeleted() {
        return QueueBuilder.durable(EventsConstants.QUEUE_EVENTS_PERSON_DELETED).build();
    }

    @Bean
    public Binding bindingQueueEventsPersonDeleted() {
        return BindingBuilder.bind(queueEventsPersonDeleted()).to(fanoutExchangePersonDeleted());
    }
}
