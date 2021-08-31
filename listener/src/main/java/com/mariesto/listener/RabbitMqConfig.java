package com.mariesto.listener;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value ("${rabbitmq.queue-name}")
    private String QUEUE_NAME;

    @Value ("${rabbitmq.exchange}")
    private String TOPIC_EXCHANGE;

    @Value ("${rabbitmq.routing-key}")
    private String ROUTING_KEY;

    @Value ("${rabbitmq.connection.host}")
    private String HOST;

    @Value ("${rabbitmq.connection.username}")
    private String USERNAME;

    @Value ("${rabbitmq.connection.password}")
    private String PASSWORD;

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true, false, false);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(HOST);
        cachingConnectionFactory.setUsername(USERNAME);
        cachingConnectionFactory.setPassword(PASSWORD);
        return cachingConnectionFactory;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory());
        messageListenerContainer.setQueues(queue());
        messageListenerContainer.setMessageListener(new RabbitMqListener());
        return messageListenerContainer;
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY).noargs();
    }

}
