package br.com.lucio.order.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("amqpConfiguration")
public class QueuePersonReplicationConfig {

    @Bean
    public FanoutExchange fanoutExchangeCrudPerson() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PERSON_CRUD).build();
    }
    @Bean
    public Queue queueCrudEventPerson() {
        return QueueBuilder.durable(EventsConstants.QUEUE_EVENTS_PERSON_CRUD).build();
    }

    @Bean
    public Binding bindingQueueCrudEventPerson() {
        return BindingBuilder.bind(queueCrudEventPerson()).to(fanoutExchangeCrudPerson());
    }

}
