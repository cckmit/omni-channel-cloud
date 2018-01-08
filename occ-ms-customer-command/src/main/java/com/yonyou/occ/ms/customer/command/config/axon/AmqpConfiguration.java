package com.yonyou.occ.ms.customer.command.config.axon;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AmqpConfiguration {
    @Value("${axon.amqp.exchange}")
    private String axonAmqpExchange;

    @Bean
    public Serializer axonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(axonAmqpExchange).durable(true).build();
    }

    @Bean
    public Queue customerAccountQueue() {
        return QueueBuilder.durable("customeraccount").build();
    }

    @Bean
    public Binding customerAccountQueueBinding() {
        return BindingBuilder.bind(customerAccountQueue()).to(exchange()).with("#.customeraccount.#").noargs();
    }
}
