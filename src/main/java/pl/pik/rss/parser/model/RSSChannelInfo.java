package pl.pik.rss.parser.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
@Data
public class RSSChannelInfo {
    private String title;
    private String description;
    private String link;
    private String language;
}
