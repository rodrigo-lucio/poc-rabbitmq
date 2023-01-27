package br.com.lucio.person.infra.config.amqp;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("amqpConfiguration")
public class QueuePersonReplicationConfig {

    @Bean
    public FanoutExchange fanoutExchangePersonCreated() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PERSON_CREATED).build();
    }

    @Bean
    public FanoutExchange fanoutExchangePersonUpdated() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PERSON_UPDATED).build();
    }

    @Bean
    public FanoutExchange fanoutExchangePersonDeleted() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PERSON_DELETED).build();
    }

}
