package br.com.lucio.payment.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("amqpConfiguration")
public class QueuePaymentGatewayYourselfConfig {

    @Bean
    public TopicExchange topicExchangePaymentGatewayYourself() {
        return ExchangeBuilder.topicExchange(EventsConstants.EXCHANGE_PAYMENT_YOURSELF).build();
    }
    @Bean
    public Queue queueEventPaymentGatewayYourself() {
        return QueueBuilder.durable(EventsConstants.QUEUE_PAYMENT_PROCESS_GATEWAY_YOURSELF).build();
    }

    @Bean
    public Binding bindingQueuePaymentGateway() {
        return BindingBuilder.bind(queueEventPaymentGatewayYourself())
                .to(topicExchangePaymentGatewayYourself())
                .with(EventsConstants.QUEUE_PAYMENT_PROCESS_GATEWAY_YOURSELF);
    }

}
