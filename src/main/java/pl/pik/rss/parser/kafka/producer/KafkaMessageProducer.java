package pl.pik.rss.parser.kafka.producer;


import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;
import pl.pik.rss.parser.model.ParsedMessage;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaMessageProducer {
    private static final String KAFKA_BROKERS = "52.169.28.113:9092, 52.169.28.113:9093";
    private static final String CLIENT_ID = "rssCrawler";
    private static final String TOPIC_NAME = "testTopic";

    private Producer<String, ParsedMessage> producer;

    public KafkaMessageProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ParseredMessageSerializer.class.getName());

        producer = new KafkaProducer<>(properties);
    }

    public void produce(ParsedMessage parsedMessage) {
        System.out.println(parsedMessage);
        try {
            RecordMetadata recordMetadata = producer.send(new ProducerRecord<>(TOPIC_NAME, parsedMessage)).get();
            System.out.println(recordMetadata.topic());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
