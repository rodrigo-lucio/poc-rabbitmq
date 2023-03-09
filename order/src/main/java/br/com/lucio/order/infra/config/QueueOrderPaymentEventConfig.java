package br.com.lucio.order.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("amqpConfiguration")
public class QueueOrderPaymentEventConfig {

    @Bean
    public FanoutExchange fanoutExchangePaymentEvents() {
        return ExchangeBuilder.fanoutExchange(EventsConstants.EXCHANGE_EVENTS_PAYMENT_CREATED).build();
    }
    @Bean
    public Queue queueEventPaymentCreated() {
        return QueueBuilder.durable(EventsConstants.QUEUE_EVENTS_PAYMENT_CREATED).build();
    }

    @Bean
    public Binding bindingQueuePaymentCreated() {
        return BindingBuilder.bind(queueEventPaymentCreated()).to(fanoutExchangePaymentEvents());
    }


}
