package common;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public abstract class AbstractKafkaProducer<K, V> extends BaseKafkaClient {

    protected KafkaProducer<K, V> producer;

    public AbstractKafkaProducer() {
        super();
        Properties properties = createProducerProperties();
        this.producer = new KafkaProducer<>(properties);
    }

    protected Properties createProducerProperties() {
        Properties properties = loadCommonProperties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configureAdditionalProperties(properties);
        return properties;
    }

    protected abstract String getBootstrapServers();

    protected void configureAdditionalProperties(Properties properties) {
        // This method can be overridden by subclasses to add additional properties
    }

    public abstract void produce();

    @Override
    public void close() {
        if (producer != null) {
            producer.close();
        }
        log.info("Kafka Producer closed.");
    }
}
