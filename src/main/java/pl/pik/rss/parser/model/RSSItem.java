package pl.pik.rss.parser.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class RSSItem {
    private String title;
    private String description;

    @JsonProperty("description")
    private void unpackNested(Map<String, Object> description) {
        this.description = (String) description.get("value");
    }

    private String link;
    private Date publishedDate;
    private String imgUrl;
}

