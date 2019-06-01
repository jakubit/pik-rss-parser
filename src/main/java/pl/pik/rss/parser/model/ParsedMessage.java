package pl.pik.rss.parser.model;

import lombok.*;

@ToString
@Getter
@EqualsAndHashCode
@Data
@AllArgsConstructor
public class ParsedMessage {
    private RSSChannelInfo rssChannelInfo;
    private RSSItem rssItem;
}
