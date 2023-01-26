package br.com.lucio.order.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class AmqpConfiguration {

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter(objectMapper);
        return jackson2JsonMessageConverter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

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
