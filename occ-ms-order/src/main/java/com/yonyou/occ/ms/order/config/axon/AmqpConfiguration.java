package com.yonyou.occ.ms.order.config.axon;

import com.rabbitmq.client.Channel;
import com.yonyou.occ.ms.order.handler.PurchaseOrderEventHandler;
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
    public Queue orderQueue() {
        return QueueBuilder.durable("order").build();
    }

    @Bean
    public Binding orderQueueBinding() {
        return BindingBuilder.bind(orderQueue()).to(exchange()).with("#.order.#").noargs();
    }

    @Bean
    public SpringAMQPMessageSource orderQueueMessageSource(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer) {
            @RabbitListener(queues = "order")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                log.debug("Order message received: " + message.toString());
                super.onMessage(message, channel);
            }
        };
    }

    /**
     * Make OrderEventHandler subscribe event from outside(MQ). It's very important!!!
     *
     * @param purchaseOrderEventHandler PurchaseOrderEventHandler component instance.
     * @return EventProcessor for Purchase Order and Sale Order.
     */
    @Bean
    public EventProcessor orderEventProcessor(PurchaseOrderEventHandler purchaseOrderEventHandler) {
        EventHandlerInvoker eventHandlerInvoker = new SimpleEventHandlerInvoker(purchaseOrderEventHandler);
        EventProcessor eventProcessor = new SubscribingEventProcessor("orderEventProcessor", eventHandlerInvoker,
            orderQueueMessageSource(axonSerializer()));
        eventProcessor.start();
        return eventProcessor;
    }
}
