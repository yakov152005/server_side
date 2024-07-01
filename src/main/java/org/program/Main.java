package org.program;
import com.fasterxml.jackson.core.JsonProcessingException;
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

public class Main {

    public static CloseableHttpClient client = HttpClients.createDefault();
    public static Scanner s = new Scanner(System.in);
    public static final int ERROR_0 = 1000;
    public static final int ERROR_1 = 1001;
    public static final int ERROR_2 = 1002;
    public static final int ERROR_3 = 1003;
    public static final int ERROR_4 = 1004;
    public static final int ERROR_5 = 1005;
    public static final int ERROR_6 = 1006;
    public static final int ERROR_7 = 1007;

    public String userId;
    private String task;

    public Main() throws IOException, URISyntaxException {
        this.userId = null;
        this.task = null;

        while (true) {

            mainMenu();
            String chooseStr = s.nextLine();
            int choose = stringToInt(chooseStr);

            if (choose == 0){
                System.out.println("Exit...");
                break;
            }
            switch (choose) {
                case 1:
                    userId = inputString();
                    registerUser(userId);
                    break;
                case 2:
                    userId = inputString();
                    getUserTask(userId);
                    break;
                case 3:
                    userId = inputString();
                    task = inputTask();
                    addTask(userId,task);
                    break;
                case 4:
                    userId = inputString();
                    this.task = inputTask();
                    setTaskDone(userId,task);
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    public static void registerUser(String userId) throws IOException, URISyntaxException {
        URI uriBuilder = getUriBuilder("register")
                        .setParameter("id",userId)
                        .build();
        String myResponse = postAndResponse(uriBuilder);
        Response responseObj = getResponseObj(myResponse);
        if (responseObj.isSuccess()) {
            System.out.println("You have successfully registered");
        }else {
            errorCode(responseObj,userId);
        }

    }

    public static void getUserTask(String userId) throws IOException, URISyntaxException {
        URI uriBuilder = getUriBuilder("get-tasks")
                .setParameter("id",userId)
                .build();
        String myResponse = getAndResponse(uriBuilder);
        Response responseObj = getResponseObj(myResponse);
        StringBuilder sb = new StringBuilder();
        if (responseObj.isSuccess()){
            sb.append("You have ").append(responseObj.getTasks()
                    .stream()
                    .filter(taskModel -> !taskModel.isDone())
                    .toList()
                    .size());
            sb.append(" open tasks!").append("\n");
            for (int i = 0; i < responseObj.getTasks().size(); i++) {
                sb.append((i+1)).append(": ").append(responseObj.getTasks().get(i).getTitle()).
                        append((responseObj.getTasks().get(i).isDone() ? " - DONE" : " - NOT DONE")).append("\n");
            }
        }else {
            errorCode(responseObj,userId);
        }
        System.out.println(sb);
    }

    public static void addTask(String userId, String text)throws IOException , URISyntaxException{
        URI uriBuilder = getUriBuilder("add-task")
                .setParameter("id",userId)
                .setParameter("text",text)
                .build();
        String request = postAndResponse(uriBuilder);
        if (request.isEmpty()){
            System.out.println("The task is added. " + request);
        }
        else {
            Response responseObj = getResponseObj(request);
            errorCode(responseObj, userId);
        }

    }

    public static void setTaskDone(String userId, String text)throws IOException, URISyntaxException{
        URI uriBuilder = getUriBuilder("set-task-done")
                .setParameter("id",userId)
                .setParameter("text",text)
                .build();
        String request = postAndResponse(uriBuilder);
        Response responseObj = getResponseObj(request);
        if (responseObj.isSuccess()){
            System.out.println("The task --> " + text + " Is Done.");
        }else {
            errorCode(responseObj,userId);
        }
    }

    public static void mainMenu(){
        System.out.println("What to u want to do ? ");
        System.out.println("""
                    1 - Register to the system
                    2 - Get a list of tasks saved for a specific user
                    3 - Add a task to a specific user
                    4 - Mark a task as done
                    0 - Exit.
                    """);
    }

    public static int stringToInt(String str){
        int choose = 5;
        try{
            choose = Integer.parseInt(str);
        }catch (NumberFormatException e){}
        return choose;
    }

    public static String inputString(){
        System.out.println("Enter your ID:");
        return s.nextLine();
    }

    public static String inputTask(){
        System.out.println("Enter task: ");
        return s.nextLine();
    }

    public static URIBuilder getUriBuilder(String httpsName) throws URISyntaxException {
        return new URIBuilder("https://app.seker.live/fm1/" + httpsName);
    }

    public static String postAndResponse(URI uriBuilder) throws IOException {
        HttpPost request = new HttpPost(uriBuilder);
        CloseableHttpResponse response = client.execute(request);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println("HTTP Status: " +(response.getStatusLine().getStatusCode() == 200 ? "תקין" : "לא תקין" ) );  // הדפסת קוד הסטטוס של HTTP
        response.close();
        return result;
    }

    public static String getAndResponse(URI uriBuilder)throws IOException{
        HttpGet get = new HttpGet(uriBuilder);
        CloseableHttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());
    }

    public static Response getResponseObj(String myResponse) throws JsonProcessingException {
        if (myResponse == null || myResponse.isEmpty()) {
            throw new JsonProcessingException("Response is empty") {};
        }
        Response response;
        response = new ObjectMapper().readValue(myResponse, Response.class);
        return response;
    }

    public static void errorCode(Response responseObj, String userId){

        if (responseObj.getErrorCode() == ERROR_2 || responseObj.getErrorCode() == ERROR_0 ) {
            System.out.println("The id is not sent.");
        } else if (responseObj.getErrorCode() == ERROR_3) {
            System.out.println("You are already in the system.");
        } else if (responseObj.getErrorCode() == ERROR_1) {
            System.out.println("A user with this ID -- > '" + userId  + "' has not been registered!");
        }else if (responseObj.getErrorCode() == ERROR_4){
            System.out.println("The text is never send.");
        } else if (responseObj.getErrorCode() == ERROR_5) {
            System.out.println("For the current user --> '" + userId + "' there is an identical open task.");
        } else if (responseObj.getErrorCode() == ERROR_6) {
            System.out.println("This task does NOT exist in this user.");
        } else if (responseObj.getErrorCode() == ERROR_7) {
            System.out.println("This task is Marked Done. ");
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        new Main();
    }
}
