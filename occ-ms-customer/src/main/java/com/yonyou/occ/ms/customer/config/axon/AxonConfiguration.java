package com.yonyou.occ.ms.customer.config.axon;

import java.util.Collections;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public Serializer axonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    public MongoClient mongoClient() {
        MongoFactory mongoFactory = new MongoFactory();
        mongoFactory.setMongoAddresses(Collections.singletonList(new ServerAddress(mongoHost, mongoPort)));
        return mongoFactory.createMongo();
    }

    @Bean(name = "axonMongoTemplate")
    public MongoTemplate axonMongoTemplate() {
        return new DefaultMongoTemplate(mongoClient(), mongoDatabase).withDomainEventsCollection(
            domainEventsCollectionName).withSnapshotCollection(snapshotEventsCollectionName).withSagasCollection(
            sagasCollectionName).withTrackingTokenCollection(trackingTokensCollectionName);
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
}
