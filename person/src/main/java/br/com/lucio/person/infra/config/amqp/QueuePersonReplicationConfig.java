package br.com.lucio.person.infra.config.amqp;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("amqpConfiguration")
public class QueuePersonReplicationConfig {

    @Bean
    public FanoutExchange fanoutExchangePersonCreated() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PERSON_CRUD).build();
    }

}
