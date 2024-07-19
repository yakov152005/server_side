package org.program.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.program.jaksonclass.Response;
import org.program.joke.Joke;
import org.program.numbers.Numbers;
import org.program.quotes.Quotes;
import org.program.trivia.Question;
import org.program.trivia.TriviaGame;
import org.program.utils.Constants;
import org.program.utils.ErrorOption;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


import static org.program.utils.Constants.CategoriesJoke.DEF_JOKE;
import static org.program.utils.Constants.CategoriesQuestion.TRIVIA_OPTIONS;
import static org.program.utils.Constants.ErrorText.*;
import static org.program.utils.Constants.Path.*;
import static org.program.utils.Constants.PathGpt.*;
import static org.program.utils.Constants.Text.*;


public class Main {

    public static final ObjectMapper mapper = new ObjectMapper();
    public static final CloseableHttpClient client = HttpClients.createDefault();
    public static CloseableHttpResponse closeableHttpResponse;
    public static final StringBuilder sb = new StringBuilder();
    public static final HttpGet get = new HttpGet();
    public static final HttpPost post = new HttpPost();
    public static Response response = new Response();
    public static Scanner s = new Scanner(System.in);
    public static final AtomicInteger forPoint = new AtomicInteger();

    private Joke joke;
    private boolean run;
    private String userId;
    private String task;
    private int select;

    public Main() throws URISyntaxException, IOException {
        this.userId = null;
        this.task = null;
        this.run = true;
        closeableHttpResponse = null;

        while (run) {
            System.out.println(MAIN_MENU);

            String selectStr = s.nextLine();
            this.select = stringToInt(selectStr);

            if (select == 0) {
                System.err.println("Good Bye..");
                run = false;
            }

            switch (select) {
                case 1 -> switchCaseGpt();
                case 2 -> switchCaseTasks();
                case 3 -> switchCaseJoke();
                case 4 -> switchCaseTriviaGame();
                case 5 -> switchCaseNumbersFact();
                case 6 -> switchCaseQuotes();
                case 0 -> System.exit(0);
                default -> System.out.println(DEF_2);
            }

        }

    }

    private void switchCaseQuotes() throws URISyntaxException, IOException {
        List<Quotes> quotesList;

        while (true) {
            System.out.println(MENU_6);
            String choiceStr = s.nextLine();
            int choice = stringToInt(choiceStr);

            if (choice == 0) {
                System.out.println(BACK);
                return;
            }

            if (choice < 0 || choice > 2) {
                System.out.println(DEF_1);
                return;
            }

                Constants.EnumQuotes category = Constants.EnumQuotes.fromInt(choice);
                switch (category) {
                    case RANDOM, ALBERT_EINSTEIN:
                        quotesList = Quotes.getQuotes(category);
                        if (quotesList != null && !quotesList.isEmpty()) {
                            System.out.println(quotesList.get(new Random().nextInt(quotesList.size())));
                        } else {
                            System.out.println(DEF_3);
                        }
                        break;
                    default:
                        System.out.println(DEF_1);
                        break;
                }
        }
    }




    private static void switchCaseNumbersFact() throws URISyntaxException, IOException {
        Numbers numbers;

        while (true){
            System.out.println(MENU_5);
            String choiceStr = s.nextLine();
            int choice = stringToInt(choiceStr);

            if (choice < 0 || choice > 4){
                System.err.println(DEF_1);
                return;
            }

            if (choice == 0){
                System.err.println(BACK);
                return;
            }

            Constants.FactNumber factNumber = Constants.FactNumber.fromInt(choice);
            switch (factNumber){
                case MATH,DATE,YEAR,TRIVIA:
                    numbers = Numbers.getFact(factNumber);
                    if (numbers != null && numbers.isFound()) {
                        System.out.println(numbers.getType() + "\n" + "\t" + numbers.getText());
                    }else {
                        System.out.println("Not Found.");
                    }
                    break;
                case null, default:
                    System.out.println(DEF_JOKE);
                    break;
            }
        }


    }


    private void switchCaseTriviaGame() throws URISyntaxException, IOException {

        while (true) {
            System.out.println(MENU_4);
            String choiceStr = s.nextLine();
            int choice = stringToInt(choiceStr);

            if (choice < 0 || choice > TRIVIA_OPTIONS.length){
                System.out.println(DEF_1);
                System.err.println(BACK);
                return;
            }

            Constants.QuestionCategory questionCategory = Constants.QuestionCategory.fromInt(choice);

            if (questionCategory == Constants.QuestionCategory.EXIT){
                System.err.println(BACK);
                return;
            }


            TriviaGame triviaGame = getTriviaQuestion(questionCategory);
            int point = 0;
            if (triviaGame.getResponse_code() == 0) {
                List<Question> questionList = triviaGame.getResults();
                for (Question question : questionList) {
                    String correctAnswer = question.getCorrect_answer();
                    System.out.println(question.toString());
                    System.out.println("Choose the correct answer -- > ");
                    String answerUser = s.nextLine();

                    if (answerUser.equalsIgnoreCase(correctAnswer)) {
                        point = forPoint.addAndGet(point + 1);
                        System.out.println("Correct! ,Your point is -- > " + point);
                    } else {
                        point = forPoint.addAndGet(point - 1);
                        System.out.println("Incorrect answer :(, Your point is -- > " + point);
                    }
                }
            }
        }
    }


    private TriviaGame getTriviaQuestion(Constants.QuestionCategory category) throws URISyntaxException, IOException {
        URI uri = new URI(Constants.CategoriesQuestion.TRIVIA_OPTIONS[category.ordinal()]);
        HttpGet get = new HttpGet(uri);
        CloseableHttpResponse chr = client.execute(get);
        String request = EntityUtils.toString(chr.getEntity());
        return mapper.readValue(request,TriviaGame.class);
    }


        private void switchCaseJoke()throws URISyntaxException{
        joke = new Joke();
        Joke defJoke = new Joke(DEF_JOKE);
        while (true){

            System.out.println(MENU_3);
            String selectStr = s.nextLine();
            int select = stringToInt(selectStr);


            if (select < 0 || select > 7){
                System.out.println(DEF_1);
                return;
            }

            Constants.JokeCategory category = Constants.JokeCategory.fromInt(select);

            switch (category){
                case DARK, ANY, MISC, PROGRAMMING, PUN, SPOOKY, CHRISTMAS :
                        joke = Joke.getJoke(category);
                        if (joke != defJoke) {
                            if (joke.getType().equalsIgnoreCase("TwoPart")) {
                                System.out.println(joke.getSetup() + "\n" + joke.getDelivery());
                            } else {
                                System.out.println(joke.getDelivery());
                            }
                        }else {
                            System.out.println(defJoke.getDelivery());
                        }
                    break;
                case EXIT:
                    System.err.println("Back to the main menu.. ");
                    return;
                default:
                    System.err.println("Invalid Option. ");
                    break;
            }
        }
    }

    private void switchCaseGpt() throws URISyntaxException, IOException {
        while (true) {
            System.out.println(MENU_2);
            String selectStr = s.nextLine();
            int select = stringToInt(selectStr);

            if (select == 0) {
                System.out.println("Exit...");
                return;
            }

            Constants.OptionSwitch option = Constants.OptionSwitch.fromInt(select);

            if (option == null) {
                System.err.println("Invalid selection. Try again.");
                continue;
            }

            switch (option) {
                case C1 -> loopGpt();
                case C2 -> clearHistory();
                case C3 -> checkBalance();
                default -> System.err.println("Try again.");
            }
        }
    }

    private static void loopGpt() throws URISyntaxException, IOException {
        Thread reminder = thread();
        System.err.println("If you want to exit, please enter a keyword for goodbye.");
        while (true){
            String text = inputText();
            if (text.equalsIgnoreCase("exit") ||
                    text.equalsIgnoreCase("לצאת") ||
                    text.equalsIgnoreCase("ביי") ||
                    text.equalsIgnoreCase("Bye") ||
                    text.equalsIgnoreCase("להתראות")||
                    text.equalsIgnoreCase("Good Bye")||
                    text.equalsIgnoreCase("goodbye")){
                System.err.println("Back to the menu...");reminder.interrupt();return;}
            sendMessage(text);
        }
    }



    private static String inputText(){
        System.out.println("Enter message: ");
        return s.nextLine();
    }

    public static Thread thread (){
        Thread reminderThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5 * 60 * 1000);
                    System.out.println("If you want to exit, please enter a keyword for goodbye.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        reminderThread.start();
        return reminderThread;
    }

    private static void sendMessage(String text)throws URISyntaxException,IOException{
        URI uri = getUri(SEND_MESSAGE)
                .setParameter(ID,ID_GPT)
                .setParameter(TEXT,text)
                .build();

        HttpGet get = new HttpGet(uri);
        CloseableHttpResponse chr = client.execute(get);
        String myResponse = EntityUtils.toString(chr.getEntity());
        if (!myResponse.isEmpty()){
            Response responseObj = mapper.readValue(myResponse,Response.class);
            if (responseObj.isSuccess()){
                System.out.println(responseObj.getExtra());
            }else {
                errorCode(responseObj,ID_GPT);
            }
        }else {
            System.err.println("The message was not sent.");
        }

    }

    public static void clearHistory() throws URISyntaxException, IOException {
        URI uri = getUri(CLEAR_HISTORY)
                .setParameter(ID,ID_GPT)
                .build();
        get.setURI(uri);
        CloseableHttpResponse chr = client.execute(get);
        String myResponse = EntityUtils.toString(chr.getEntity());
        if (!myResponse.isEmpty()){
            Response responseObj = mapper.readValue(myResponse,Response.class);
            if (responseObj.isSuccess()){
                System.err.println("Clear chat...");
            }else {
                errorCode(responseObj,ID_GPT);
            }
        }
    }

    public static void checkBalance()throws URISyntaxException, IOException{
        URI uri = getUri(CHECK_BALANCE)
                .setParameter(ID,ID_GPT)
                .build();
        get.setURI(uri);
        CloseableHttpResponse chr = client.execute(get);
        String myResponse = EntityUtils.toString(chr.getEntity());
        if (!myResponse.isEmpty()){
            Response responseObj = mapper.readValue(myResponse,Response.class);
            if (responseObj.isSuccess()){
                System.err.println("--> Do u have |" + responseObj.getExtra() + "| message in the balance.");
            }else {
                errorCode(responseObj,ID_GPT);
            }
        }
    }

    private void switchCaseTasks() throws URISyntaxException, IOException {
        
        while (true){
            System.out.println(MENU_1);
            String selectStr = s.nextLine();
            int select = stringToInt(selectStr);

            String userId;
            String task;
            
            if (select == 0){
                System.err.println("Exit...");
                return;
            }

            switch (select){
                case 1:
                    userId = enterName();
                    registerUser(userId);
                    break;
                case 2:
                    userId = enterName();
                    getTasks(userId);
                    break;
                case 3:
                    userId = enterName();
                    task = enterTask();
                    addTasks(userId,task);
                    break;
                case 4:
                    userId = enterName();
                    task = enterTask();
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
            System.err.println("ERROR, Please input integer.");
        }
        return -1;
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
        if (responseObj.isSuccess()) {
            sbTask.append("You have ").
                    append((responseObj.getTasks()
                            .stream()
                            .filter(taskModel -> !taskModel.isDone())
                                    .toList()
                                            .size()));
                    sbTask.append(" open tasks!").append("\n");
            for (int i = 0; i < responseObj.getTasks().size(); i++) {
                sbTask.append((i + 1)).
                        append(": ").
                        append(responseObj.getTasks().get(i).getTitle()).
                        append(" - ").
                        append((responseObj.getTasks().get(i).done ? " DONE" : " NOT DONE")).
                        append("\n");
            }
            System.out.println(sbTask);
        }
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
                case E_0, E_2, E_3, E_4, E_6, E_7, E_3_0, E_3_1, E_3_2, E_3_3, E_3_5 ->
                        sb.append(e.getCode()).append(LINE).append(e.getMessage());
                case E_1, E_5 ->
                        sb.append(e.getCode()).append(USER).append(userId).append(" ").append(e.getMessage());
                default ->
                        System.out.println(TEXT_8 + responseObj.getErrorCode());
            }
            System.out.println(sb);
        } else {
            System.out.println(TEXT_8 + responseObj.getErrorCode());
        }
    }


    public static void main(String[] args) throws URISyntaxException, IOException {
        new Main();
    }

}


