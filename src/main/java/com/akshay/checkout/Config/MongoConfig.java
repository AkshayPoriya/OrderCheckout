package com.akshay.checkout.Config;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(MongoConfigProperties.class)
public class MongoConfig {
    @Autowired
    private MongoConfigProperties mongoConfigProperties;

    @Bean
    @Qualifier(value = ApplicationConstants.CHECKOUT_MONGO_TEMPLATE_BEAN_NAME)
    public MongoTemplate checkoutMongoTemplate() throws Exception {
        return new MongoTemplate(checkoutMongoClient(), mongoConfigProperties.getDatabase());
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(checkoutMongoClient(), mongoConfigProperties.getDatabase());
    }

    @Bean
    @Qualifier
    public MongoClient checkoutMongoClient() {
        if (mongoConfigProperties.getUri().isBlank()) {
            System.exit(1);
        }
        final ConnectionString connectionString = new ConnectionString(
                mongoConfigProperties.getUri());

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(
                                mongoConfigProperties.getConnectionMaxIdleTimeMs(), TimeUnit.MILLISECONDS)
                        .maxConnectionLifeTime(mongoConfigProperties.getConnectionMaxLifeTimeMs(),
                                TimeUnit.MILLISECONDS)
                        .maxSize(mongoConfigProperties.getPoolMaxSize())
                        .minSize(mongoConfigProperties.getPoolMinSize())
                        .build())
                .build();
        return MongoClients.create(mongoClientSettings);
    }
}
