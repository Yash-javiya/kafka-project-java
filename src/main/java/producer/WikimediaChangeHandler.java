package producer;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikimediaChangeHandler implements BackgroundEventHandler {

    private static final Logger log = LoggerFactory.getLogger(WikimediaChangeHandler.class.getSimpleName());
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public WikimediaChangeHandler(KafkaProducer<String, String> producer, String topic) {
        this.producer = producer;
        this.topic = topic;
    }

    @Override
    public void onOpen() {
        // Logic for when the connection is opened
        log.info("Opened connection");
    }

    @Override
    public void onClosed() {
        // Logic for when the connection is closed
        producer.close();
        log.info("Closed connection");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        // Logic for when a message is received
        producer.send(new ProducerRecord<>(topic, messageEvent.getData()));
    }

    @Override
    public void onComment(String comment) {
        // Handle any comments if necessary
    }

    @Override
    public void onError(Throwable t) {
        // Handle any errors that occur
        log.error("Error in Stream Reading :", t);
    }
}
