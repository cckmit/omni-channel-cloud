package com.yonyou.occ.ms.customer.config.axon;

import com.rabbitmq.client.Channel;
import com.yonyou.occ.ms.customer.handler.CustomerAccountEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.eventhandling.EventHandlerInvoker;
import org.axonframework.eventhandling.EventProcessor;
import org.axonframework.eventhandling.SimpleEventHandlerInvoker;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @Bean
    public SpringAMQPMessageSource customerAccountQueueMessageSource(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer) {
            @RabbitListener(queues = "customeraccount")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                log.debug("CustomerAccount message received: " + message.toString());
                super.onMessage(message, channel);
            }
        };
    }

    /**
     * Make CustomerAccountEventHandler subscribe event from outside(MQ). It's very important!!!
     *
     * @param eventHandler CustomerAccountEventHandler component instance.
     * @return EventProcessor for CustomerAccount.
     */
    @Bean
    public EventProcessor customerAccountEventProcessor(CustomerAccountEventHandler eventHandler) {
        EventHandlerInvoker eventHandlerInvoker = new SimpleEventHandlerInvoker(eventHandler);
        EventProcessor eventProcessor = new SubscribingEventProcessor("customerAccountEventProcessor",
            eventHandlerInvoker, customerAccountQueueMessageSource(axonSerializer()));
        eventProcessor.start();
        return eventProcessor;
    }
}
