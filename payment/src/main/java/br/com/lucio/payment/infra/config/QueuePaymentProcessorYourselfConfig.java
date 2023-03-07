package br.com.lucio.payment.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("amqpConfiguration")
public class QueuePaymentProcessorYourselfConfig {

    @Bean
    public TopicExchange topicExchangePaymentProcessorYourself() {
        return ExchangeBuilder.topicExchange(EventsConstants.EXCHANGE_PAYMENT_YOURSELF).build();
    }
    @Bean
    public Queue queueEventPaymentProcessorYourself() {
        return QueueBuilder.durable(EventsConstants.QUEUE_PAYMENT_PROCESS_PAYMENT_YOURSELF).build();
    }

    @Bean
    public Binding bindingQueuePaymentProcessor() {
        return BindingBuilder.bind(queueEventPaymentProcessorYourself())
                .to(topicExchangePaymentProcessorYourself())
                .with(EventsConstants.QUEUE_PAYMENT_PROCESS_PAYMENT_YOURSELF);
    }

}
