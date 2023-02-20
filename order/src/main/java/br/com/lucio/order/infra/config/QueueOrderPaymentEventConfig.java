package br.com.lucio.order.infra.config;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("amqpConfiguration")
public class QueueOrderPaymentEventConfig {

    @Bean
    public FanoutExchange fanoutExchangePaymentCreated() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PAYMENT_CREATED).build();
    }

}
