package com.yonyou.occ.ms.customer.command.config.axon;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.yonyou.occ.ms.customer.command.aggregate.CustomerAccountAggregate;
import com.yonyou.occ.ms.customer.command.handler.CustomerAccountCommandHandler;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * Custom Axon configuration on command side.
 *
 * @author WangRui
 * @date 2018-01-05 11:49:21
 */
@Configuration
public class AxonConfiguration {
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private int mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;

    @Value("${spring.data.mongodb.domain-events-collection-name}")
    private String domainEventsCollectionName;

    @Value("${spring.data.mongodb.snapshot-events-collection-name}")
    private String snapshotEventsCollectionName;

    @Value("${spring.data.mongodb.sagas-collection-name}")
    private String sagasCollectionName;

    @Value("${spring.data.mongodb.tracking-tokens-collection-name}")
    private String trackingTokensCollectionName;

    @Autowired
    private EventStore eventStore;

    @Autowired
    private AggregateFactory<CustomerAccountAggregate> customerAccountAggregateFactory;

    @Bean
    public Serializer axonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    public MongoClient mongoClient() {
        MongoFactory mongoFactory = new MongoFactory();
        mongoFactory.setMongoAddresses(Arrays.asList(new ServerAddress(mongoHost, mongoPort)));
        return mongoFactory.createMongo();
    }

    @Bean(name = "axonMongoTemplate")
    public MongoTemplate axonMongoTemplate() {
        MongoTemplate mongoTemplate = new DefaultMongoTemplate(mongoClient(), mongoDatabase).withDomainEventsCollection(
            domainEventsCollectionName).withSnapshotCollection(snapshotEventsCollectionName).withSagasCollection(
            sagasCollectionName).withTrackingTokenCollection(trackingTokensCollectionName);
        return mongoTemplate;
    }

    @Bean
    public EventStorageEngine eventStorageEngine() {
        return new MongoEventStorageEngine(axonSerializer(), null, axonMongoTemplate(),
            new DocumentPerEventStorageStrategy());
    }

    @Bean
    public SagaStore sagaStore() {
        return new MongoSagaStore(axonMongoTemplate(), axonSerializer());
    }


    @Bean
    public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {
        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public Snapshotter snapshotter() {
        return new AggregateSnapshotter(eventStore, customerAccountAggregateFactory);
    }

    @Primary
    @Bean
    public CommandRouter mySpringCloudCommandRouter(DiscoveryClient discoveryClient) {
        return new MySpringCloudCommandRouter(discoveryClient, new AnnotationRoutingStrategy());
    }

    @Bean
    @Scope("prototype")
    public CustomerAccountAggregate customerAccountAggregate() {
        return new CustomerAccountAggregate();
    }

    @Bean
    public AggregateFactory<CustomerAccountAggregate> customerAccountAggregateFactory() {
        SpringPrototypeAggregateFactory<CustomerAccountAggregate> aggregateFactory =
            new SpringPrototypeAggregateFactory<>();
        aggregateFactory.setPrototypeBeanName("customerAccountAggregate");
        return aggregateFactory;
    }

    @Bean
    public Repository<CustomerAccountAggregate> customerAccountAggregateRepository() {
        EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new EventCountSnapshotTriggerDefinition(
            snapshotter(), 10);
        EventSourcingRepository<CustomerAccountAggregate> repository = new EventSourcingRepository<>(
            customerAccountAggregateFactory(), eventStore, snapshotTriggerDefinition);
        return repository;
    }

    @Bean
    public CustomerAccountCommandHandler customerAccountCommandHandler() {
        return new CustomerAccountCommandHandler(customerAccountAggregateRepository());
    }
}
