package pl.pik.rss.parser.kafka.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import pl.pik.rss.parser.model.RSSChannelInfo;

import java.util.Map;

public class RSSChannelDeserializer implements Deserializer<RSSChannelInfo>{
    private ObjectMapper mapper;

    public RSSChannelDeserializer() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public RSSChannelInfo deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, RSSChannelInfo.class);
        } catch (Exception e) {
            throw new RuntimeException("Cannot deserialize SyndEntry", e);
        }
    }

    @Override
    public void close() {

    }
}
