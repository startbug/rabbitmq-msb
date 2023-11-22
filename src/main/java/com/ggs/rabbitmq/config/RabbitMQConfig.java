package com.ggs.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/22 22:03
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "boot-exchange";

    public static final String QUEUE = "boot-queue";

    public static final String ROUTING_KEY = "*.black.*";

    @Bean
    public TopicExchange bootExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE).build();
    }

    @Bean
    public Queue bootQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding bootBinding(TopicExchange bootExchange, Queue bootQueue) {
        return BindingBuilder.bind(bootQueue).to(bootExchange).with(ROUTING_KEY);
    }


}
