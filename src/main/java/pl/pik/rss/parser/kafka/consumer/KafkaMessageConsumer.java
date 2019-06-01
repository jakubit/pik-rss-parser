package pl.pik.rss.parser.kafka.consumer;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.pik.rss.parser.model.RSSChannelInfo;
import pl.pik.rss.parser.model.RSSItem;
import pl.pik.rss.parser.parser.ParserService;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class KafkaMessageConsumer {
    private final String TOPIC = "demo";
    private final String BOOTSTRAP_SERVERS = "52.169.28.113:9092, 52.169.28.113:9093";

    Consumer<RSSChannelInfo, RSSItem> consumer;
    private final ParserService parserService;

    @Autowired
    public KafkaMessageConsumer(ParserService parserService) {
        this.parserService = parserService;
        consumer = createConsumer();
    }


    @Scheduled(fixedDelay = 1000)
    public void runConsumer() {
        Duration timeOutTime = Duration.ofMillis(1000);
        ConsumerRecords<RSSChannelInfo, RSSItem> consumerRecords = consumer.poll(timeOutTime);
        parserService.consume(consumerRecords);
        consumer.commitAsync();
    }


    private Consumer<RSSChannelInfo, RSSItem> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put("group.id", "test-consumer-group");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", RSSChannelDeserializer.class.getName());
        props.put("value.deserializer", RSSItemDeserializer.class.getName());
        final Consumer<RSSChannelInfo, RSSItem> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }

}
