package consumer;

import common.AbstractKafkaConsumer;
import org.apache.http.HttpHost;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.action.bulk.BulkResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;

public class OpensearchConsumer extends AbstractKafkaConsumer<String, String> {

    private static final Logger log = LoggerFactory.getLogger(OpensearchConsumer.class.getSimpleName());
    private final RestHighLevelClient openSearchClient;

    public OpensearchConsumer() {
        this.openSearchClient = createOpenSearchClient("http://localhost:9200");  // This can be externalized
    }

    public static void main(String[] args) {
        OpensearchConsumer consumer = new OpensearchConsumer();
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::close));
        consumer.consume();
    }

    @Override
    protected String getBootstrapServers() {
        return "127.0.0.1:29092";
    }

    @Override
    protected String getGroupId() {
        return "opensearch-consumer-group";
    }

    @Override
    public void consume() {
        try {
            consumer.subscribe(Collections.singletonList("wikimedia.recentchange"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                BulkRequest bulkRequest = new BulkRequest();

                for (ConsumerRecord<String, String> record : records) {
                    IndexRequest indexRequest = new IndexRequest("wikimedia")
                            .source(record.value(), XContentType.JSON);
                    bulkRequest.add(indexRequest);
                }

                if (bulkRequest.numberOfActions() > 0) {
                    BulkResponse bulkResponse = openSearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
                    log.info("Inserted {} records", bulkResponse.getItems().length);
                }
            }
        } catch (WakeupException e) {
            log.info("Received shutdown signal!");
        } catch (IOException e) {
            log.error("Error while indexing to OpenSearch", e);
        } finally {
            close();
        }
    }

    private RestHighLevelClient createOpenSearchClient(String connString) {
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(connString)));
    }

    @Override
    public void close() {
        super.close();
        try {
            if (openSearchClient != null) {
                openSearchClient.close();
            }
        } catch (IOException e) {
            log.error("Error closing OpenSearch client", e);
        }
    }
}
