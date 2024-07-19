package org.program.joke;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.net.URISyntaxException;


import static org.program.utils.Constants.*;
import static org.program.utils.Constants.CategoriesJoke.DEF_JOKE;
import static org.program.main.Main.*;
import static org.program.delete.Main.client;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Joke {

    private boolean error;
    private String type;
    private String category;
    private String setup;
    private String delivery;
    private String lang;

    public Joke(){

    }
    public Joke(String delivery){
        this.delivery = delivery;
    }

    public static Joke getJoke(JokeCategory category)throws URISyntaxException{
        URI uri = getUri(CategoriesJoke.JOKES[category.ordinal()])
                .build();
        get.setURI(uri);
        try(CloseableHttpResponse chr = client.execute(get)) {
            String myResponse = EntityUtils.toString(chr.getEntity());
            Joke joke = mapper.readValue(myResponse,Joke.class);
            if (!joke.isError()){
                return joke;
            }
        }catch (Exception e){
            System.err.println("Error get joke.");
        }
        return new Joke(DEF_JOKE);
    }

    public String getType() {
        return type;
    }


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
