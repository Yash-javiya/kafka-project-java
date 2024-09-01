package producer;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import common.AbstractKafkaProducer;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class WikimediaChangeProducer extends AbstractKafkaProducer<String, String> {

    private final String topic = "wikimedia.recentchange";

    public static void main(String[] args) throws InterruptedException {
        new WikimediaChangeProducer().produce();
    }

    @Override
    protected String getBootstrapServers() {
        return "127.0.0.1:29092";
    }

    @Override
    public void produce() {
        BackgroundEventHandler eventHandler = new WikimediaChangeHandler(producer, topic);

        String URL = "https://stream.wikimedia.org/v2/stream/mediawiki.recentchange";
        BackgroundEventSource.Builder builder = new BackgroundEventSource.Builder(eventHandler, new EventSource.Builder(URI.create(URL)));

        BackgroundEventSource eventSource = builder.build();
        eventSource.start();

        try {
            TimeUnit.MINUTES.sleep(10);  // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventSource.close();
            close();
        }
    }
}
