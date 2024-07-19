package org.program.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.program.utils.Constants.*;
import static org.program.main.Main.*;
import static org.program.main.Main.client;
import static org.program.main.Main.get;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quotes {
    private String author;
    private String content;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        private List<Quotes> results;

        public List<Quotes> getResults() {
            return results;
        }

        public void setResults(List<Quotes> results) {
            this.results = results;
        }
    }


    public static List<Quotes> getQuotes(EnumQuotes category) throws URISyntaxException, IOException {
        URI uri = getUri(QuotesCon.QUOTES[category.ordinal()])
                .build();
        get.setURI(uri);
        CloseableHttpResponse chr = client.execute(get);

        if (category == EnumQuotes.RANDOM){
            List<String> myResponse = Collections.singletonList(EntityUtils.toString(chr.getEntity()));
            return mapper.readValue(String.valueOf(myResponse), mapper.getTypeFactory().constructCollectionType(List.class, Quotes.class));
        }
        if (category == EnumQuotes.ALBERT_EINSTEIN ) {
           String myResponse2 = EntityUtils.toString(chr.getEntity());
            Result einsteinResult = mapper.readValue(myResponse2, Result.class);
            return einsteinResult.getResults();
        }
        return null;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(author).append("\n\t Say --> \n\t\t").append(content);
        return sb.toString();
    }
}