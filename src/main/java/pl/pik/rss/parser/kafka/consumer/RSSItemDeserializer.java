package pl.pik.rss.parser.kafka.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import pl.pik.rss.parser.model.RSSItem;

import java.util.Map;


public class RSSItemDeserializer implements Deserializer<RSSItem> {
    private ObjectMapper mapper;

    public RSSItemDeserializer() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Override
    public void configure(Map configs, boolean isKey) {


    }

    @Override
    public RSSItem deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, RSSItem.class);
        } catch (Exception e) {
            throw new RuntimeException("Cannot deserialize SyndEntry", e);
        }
    }

    @Override
    public void close() {

    }
}
