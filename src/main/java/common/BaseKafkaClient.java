package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public abstract class BaseKafkaClient {

    protected final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    public BaseKafkaClient() {
        // Any common initialization logic for both producer and consumer
        log.info("Initializing Kafka client: {}", getClass().getSimpleName());
    }

    protected Properties loadCommonProperties() {
        Properties properties = new Properties();
        // Load any common properties, if necessary, e.g., security settings
        configureSecurityProperties(properties);
        return properties;
    }

    protected void configureSecurityProperties(Properties properties) {
        // Optionally override in subclasses to configure security properties
    }

    public abstract void close();
}
