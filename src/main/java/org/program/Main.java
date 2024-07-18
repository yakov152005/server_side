package org.program;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;


import static org.program.Constants.ErrorText.TEXT_8;
import static org.program.Constants.Path.*;
import static org.program.Constants.Text.*;


public class Main {

    public static final ObjectMapper mapper = new ObjectMapper();
    public static final CloseableHttpClient client = HttpClients.createDefault();
    public static CloseableHttpResponse closeableHttpResponse;
    public static final StringBuilder sb = new StringBuilder();
    public static final HttpGet get = new HttpGet();
    public static final HttpPost post = new HttpPost();
    public static Response response = new Response();
    public static Scanner s = new Scanner(System.in);

    private boolean run;
    private String userId;
    private String task;
    private int select;

    public Main() throws URISyntaxException, IOException {
        this.userId = null;
        this.task = null;
        this.run = true;
        closeableHttpResponse = null;

        while (run){

            System.out.println(MENU);
            String selectStr = s.nextLine();
            this.select = stringToInt(selectStr);

            if (select == 0){
                run = false;
                System.out.println("Exit...");
                break;
            }

            switch (select){
                case 1:
                    this.userId = enterName();
                    registerUser(userId);
                    break;
                case 2:
                    this.userId = enterName();
                    getTasks(userId);
                    break;
                case 3:
                    this.userId = enterName();
                    this.task = enterTask();
                    addTasks(userId,task);
                    break;
                case 4:
                    this.userId = enterName();
                    this.task = enterTask();
                    setTaskDone(userId,task);
                    break;
                default:
                    System.out.println("Input Invalid.., Try again!");
                    break;
            }

        }

    }

    public static int stringToInt(String selectStr){
        try {
            return Integer.parseInt(selectStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public static String enterName(){
        System.out.println("Enter user Id -- > ");
        return s.nextLine();
    }

    public static String enterTask(){
        System.out.println("Enter the task -- > ");
        return s.nextLine();
    }

    public static URIBuilder getUri(String uri)throws URISyntaxException{
        return new URIBuilder(uri);
    }

    public static void registerUser(String userId) throws URISyntaxException, IOException {
        URI uri = getUri(REGISTER)
                .setParameter(ID, userId)
                .build();
        post.setURI(uri);
        String myResponse = EntityUtils.toString(client.execute(post).getEntity());
        response = mapper.readValue(myResponse,Response.class);
        if (response.isSuccess()){
            System.out.println(SUCCESS);
        }else {
            errorCode(response,userId);
        }
    }

    public static void getTasks(String userId)throws URISyntaxException, IOException{
        URI uri = getUri(GET_TASK)
                .setParameter(ID,userId)
                .build();
        HttpGet get = new HttpGet(uri);
        CloseableHttpResponse chr = client.execute(get);
        String myResponse = EntityUtils.toString(chr.getEntity());
        Response responseObj = mapper.readValue(myResponse,Response.class);
       if (responseObj.isSuccess()){
           printWithSbTasks(responseObj);
       }else {
           errorCode(responseObj,userId);
       }

    }

    public static void printWithSbTasks(Response responseObj){
        StringBuilder sbTask = new StringBuilder();
        sbTask.append("You have ").
                append((responseObj.getTasks().stream().filter(r -> responseObj.isSuccess()).count())).
                append(" open tasks!").append("\n");
        for (int i = 0; i < responseObj.getTasks().size(); i++) {
            sbTask.append((i + 1 )).
                    append(": ").
                    append(responseObj.getTasks().get(i).getTitle()).
                    append(" - ").
                    append((responseObj.getTasks().get(i).done ? " DONE" : " NOT DONE" )).
                    append("\n");
        }
        System.out.println(sbTask);
    }

    public static void addTasks(String userId, String task)throws URISyntaxException , IOException{
        URI uri = getUri(ADD_TASK)
                .setParameter(ID,userId)
                .setParameter(TEXT,task)
                .build();
        HttpPost post = new HttpPost(uri);
        CloseableHttpResponse chr = client.execute(post);
        String myResponse = EntityUtils.toString(chr.getEntity());
        if (myResponse.isEmpty()){
            System.out.println("The task --> " + task + " is add.");
        }else {
            Response response = mapper.readValue(myResponse,Response.class);
            errorCode(response,userId);
        }
    }

    public static void setTaskDone(String userId,String task)throws URISyntaxException, IOException{
        URI uri = new URIBuilder(DONE_TASK)
                .setParameter(ID,userId)
                .setParameter(TEXT,task)
                .build();
        post.setURI(uri);
        CloseableHttpResponse chr = client.execute(post);
        String myResponse = EntityUtils.toString(chr.getEntity());
        Response responseObj = mapper.readValue(myResponse,Response.class);
        if (responseObj.isSuccess()){
            System.out.println("The task -- > " + task + " is Done.");
        }else {
            errorCode(responseObj,userId);
        }
    }


    public static void errorCode(Response responseObj, String userId) {

        Integer errorCode = responseObj.getErrorCode();
        if (errorCode == null) {
            System.out.println("Error code is null");
            return;
        }

        ErrorOption e = ErrorOption.fromCode(errorCode);

        if (e != null) {
            switch (e) {
                case E_0:
                case E_2:
                    sb.append(e.getCode()).append(LINE).append(e.getMessage());
                    break;
                case E_1:
                    sb.append(e.getCode()).append(USER).append(userId).append(" ").append(e.getMessage());
                    break;
                case E_3:
                    sb.append(e.getCode()).append(LINE).append(e.getMessage());
                    break;
                case E_4:
                    sb.append(e.getCode()).append(LINE).append(e.getMessage());
                    break;
                case E_5:
                    sb.append(e.getCode()).append(USER).append(userId).append(" ").append(e.getMessage());
                    break;
                case E_6:
                    sb.append(e.getCode()).append(LINE).append(e.getMessage());
                    break;
                case E_7:
                    sb.append(e.getCode()).append(LINE).append(e.getMessage());
                    break;
                default:
                    System.out.println(TEXT_8 + responseObj.getErrorCode());
                    break;
            }
            System.out.println(sb);
        }else {
            System.out.println(TEXT_8 + responseObj.getErrorCode());
        }
    }


    public static void main(String[] args) throws URISyntaxException, IOException {
        new Main();
    }

}


