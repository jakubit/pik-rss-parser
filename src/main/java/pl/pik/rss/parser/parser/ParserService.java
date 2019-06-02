package pl.pik.rss.parser.parser;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pik.rss.parser.kafka.producer.KafkaMessageProducer;
import pl.pik.rss.parser.model.ParsedMessage;
import pl.pik.rss.parser.model.RSSChannelInfo;
import pl.pik.rss.parser.model.RSSItem;

@Service

public class ParserService {

    private final KafkaMessageProducer messageProducer;

    @Autowired
    public ParserService(KafkaMessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void consume(ConsumerRecords<RSSChannelInfo, RSSItem> consumerRecords) {
        for (ConsumerRecord record : consumerRecords) {
            RSSChannelInfo rssChannelInfo = (RSSChannelInfo) record.key();
            RSSItem rssItem = (RSSItem) record.value();
            processRSSItem(rssItem);
            messageProducer.produce(rssChannelInfo.getLink(), new ParsedMessage(rssChannelInfo, rssItem));
        }
    }

    private void processRSSItem(RSSItem rssItem) {
        String descriptionToProcess = rssItem.getDescription();
        Document parsedDescription = Jsoup.parse(descriptionToProcess).normalise();

        String imgSrc = parsedDescription.select("img").attr("src");
        String description = parsedDescription.text();

        rssItem.setImgUrl(imgSrc);
        rssItem.setDescription(description);
    }
}
