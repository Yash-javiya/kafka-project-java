package common;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public abstract class AbstractKafkaConsumer<K, V> extends BaseKafkaClient {

    protected KafkaConsumer<K, V> consumer;

    public AbstractKafkaConsumer() {
        super();
        Properties properties = createConsumerProperties();
        this.consumer = new KafkaConsumer<>(properties);
    }

    protected Properties createConsumerProperties() {
        Properties properties = loadCommonProperties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, getGroupId());
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configureAdditionalProperties(properties);
        return properties;
    }

    protected abstract String getBootstrapServers();

    protected abstract String getGroupId();

    protected void configureAdditionalProperties(Properties properties) {
        // This method can be overridden by subclasses to add additional properties
    }

    public abstract void consume();

    @Override
    public void close() {
        if (consumer != null) {
            consumer.close();
        }
        log.info("Kafka Consumer closed.");
    }
}
