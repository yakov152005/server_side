package org.program.numbers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import static org.program.main.Main.*;
import static org.program.utils.Constants.*;
import static org.program.utils.Constants.NumbersFact.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Numbers {
    private String text;
    private int year;
    private int number;
    private boolean found;
    private String type;
    private String date;


    public static Numbers getFact(FactNumber factNumber) throws URISyntaxException, IOException {
        URI uri = getUri(NUMBERS[factNumber.ordinal()])
                .build();
        get.setURI(uri);
        CloseableHttpResponse chr = client.execute(get);
        String request = EntityUtils.toString(chr.getEntity());
        Numbers response = mapper.readValue(request,Numbers.class);

        if (response.isFound()){
            return response;
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Fact -- >").append(text);
        return sb.toString();
    }
}
