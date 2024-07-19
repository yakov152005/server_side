package org.program.utils;

import static org.program.utils.Constants.Path.PATH;

public class Constants {

    public static class ErrorNum {

        public static final int ERROR_0 = 1000;
        public static final int ERROR_1 = 1001;
        public static final int ERROR_2 = 1002;
        public static final int ERROR_3 = 1003;
        public static final int ERROR_4 = 1004;
        public static final int ERROR_5 = 1005;
        public static final int ERROR_6 = 1006;
        public static final int ERROR_7 = 1007;
        public static final int ERROR_3_0 = 3000;
        public static final int ERROR_3_1 = 3001;
        public static final int ERROR_3_2 = 3002;
        public static final int ERROR_3_3 = 3003;
        public static final int ERROR_3_5 = 3005;
    }

    public static class ErrorText{

        public static final String TEXT_1 = "The id is not sent.";
        public static final String TEXT_2 = "A user with this ID has not been registered!";
        public static final String TEXT_3 = "You are already in the system.";
        public static final String TEXT_4 = "The task is never sent.";
        public static final String TEXT_5 = "For the current user there is an identical open task.";
        public static final String TEXT_6 = "This task does NOT exist in this user.";
        public static final String TEXT_7 = "This task is Marked Done.";
        public static final String TEXT_8 = "Unknown error code: ";

        public static final String TEXT_3_0 = TEXT_1;
        public static final String TEXT_3_1 = "This id is not found!";
        public static final String TEXT_3_2 = "The quota of requests for this identity card is over..";
        public static final String TEXT_3_3 = "No text message will be sent.";
        public static final String TEXT_3_5 = "General error.";

        public static final String DEF_1 = "Invalid Option.";
        public static final String DEF_2 = "Invalid Option, Try again..";
        public static final String DEF_3 = "The Fact -- > idk.";
    }

    public static class Text{


        public static final String MAIN_MENU = "What would you like to do? " + "\n" +"""
                1. Talk to GPT-4 chat
                2. View tasks
                3. Get Jokes By category
                4. Trivia Game
                5. Get Numbers Fact
                6. Get Quotes
                0. To Exit
                Please select an option:
                """;
       public static final String MENU_1 = "What to u want to do ?" + "\n" +
                    """
                    1 - Register to the system
                    2 - Get a list of tasks saved for a specific user
                    3 - Add a task to a specific user
                    4 - Mark a task as done
                    0 - Back to the main menu.
                    """;
       public static final String MENU_2 = "What to u want to do ?" + "\n" +
                """
                    1 - Start to GPT-4 chat
                    2 - Clear history
                    3 - Check balance     
                    0 - Back to the main menu.
                    """;
        public static final String MENU_3 = "Choose which category you want for all jokes:\n" +
                """
                    1 - Dark
                    2 - ANY
                    3 - MISC  
                    4 - PROGRAMMING
                    5 - Pun
                    6 - Spooky
                    7 - Christmas
                    0 - Back to the main menu.
                    """;
        public static final String MENU_4 = "Choose which category you want for all Trivia Options:\n" +
                """
                    1 - General Knowledge
                    2 - Music
                    3 - Video Games 
                    4 - Computers
                    5 - Mathematics
                    6 - Sports
                    7 - Celebrities
                    8 - Anime
                    0 - Back to the main menu.
                    """;
        public static final String MENU_5 = "Choose which category you want for all Numbers Fact Options:\n" +
                """
                    1 - Math
                    2 - Trivia
                    3 - Date
                    4 - Year 
                    0 - Back to the main menu.
                    """;
        public static final String MENU_6 = "Choose which category you want for all Quotes Options:\n" +
                """
                    1 - Random
                    2 - Famous
                    3 - History
                    4 - Albert Einstein
                    0 - Back to the main menu.
                    """;

        public static final String ID = "id";
        public static final String TEXT = "text";
        public static final String SUCCESS = "Register success!";
        public static final String LINE = " --> ";
        public static final String USER = ", User: ";
        public static final String BACK = "Back to the main menu...";

    }

    public static class Path{

        public static final String PATH = "https://app.seker.live/fm1";
        public static final String REGISTER = PATH + "/register";
        public static final String ADD_TASK = PATH + "/add-task";
        public static final String GET_TASK = PATH + "/get-tasks";
        public static final String DONE_TASK = PATH + "/set-task-done";

    }

    public static class PathGpt{

        public static final String ID_GPT = "039575329";
        public static final String SEND_MESSAGE = PATH + "/send-message";
        public static final String CLEAR_HISTORY = PATH + "/clear-history";
        public static final String CHECK_BALANCE = PATH + "/check-balance";
    }

    public enum OptionSwitch {
        C1("START"),
        C2("CLEAR"),
        C3("CHECK");

        private String text;

        OptionSwitch(String text) {
            this.text = text;
        }

        public static OptionSwitch fromInt(int value) {
            return switch (value) {
                case 1 -> C1;
                case 2 -> C2;
                case 3 -> C3;
                default -> null;
            };
        }
    }

    public static class CategoriesJoke{

        public static final String PATH = "https://v2.jokeapi.dev/joke";
        public static final String DARK = PATH + "/Dark";
        public static final String ANY = PATH + "/Any";
        public static final String MISC = PATH + "/Misc";
        public static final String PROGRAMMING = PATH + "/Programming";
        public static final String PUN = PATH + "/Pun";
        public static final String SPOOKY = PATH + "/Spooky";
        public static final String CHRISTMAS = PATH + "/Christmas";
        public static final String[] JOKES = {DARK,ANY,MISC,PUN,PROGRAMMING,SPOOKY,CHRISTMAS};
        public static final String DEF_JOKE = "Write me a thousand times I'm sorry in a creative way ;) ";
    }

    public enum JokeCategory {
        DARK, ANY, MISC, PROGRAMMING, PUN, SPOOKY, CHRISTMAS,EXIT;

        public static JokeCategory fromInt(int value) {
            return switch (value) {
                case 1 -> DARK;
                case 2 -> ANY;
                case 3 -> MISC;
                case 4 -> PROGRAMMING;
                case 5 -> PUN;
                case 6 -> SPOOKY;
                case 7 -> CHRISTMAS;
                case 0 -> EXIT;
                default -> throw new IllegalArgumentException("Invalid category value: " + value);
            };
        }
    }

    public static class CategoriesQuestion{

        public static final String PATH = "https://opentdb.com/api.php?amount=1&type=multiple";
        public static final String GENERAL_KNOWLEDGE = PATH + "&category=9";
        public static final String MUSIC = PATH + "&category=12";
        public static final String VIDEO_GAMES = PATH + "&category=15";
        public static final String COMPUTERS = PATH + "&category=18";
        public static final String MATHEMATICS = PATH + "&category=19";
        public static final String SPORTS = PATH + "&category=21";
        public static final String CELEBRITIES  = PATH + "&category=26";
        public static final String ANIME = PATH + "&category=31";
        public static final String[] TRIVIA_OPTIONS = {GENERAL_KNOWLEDGE,MUSIC,VIDEO_GAMES, COMPUTERS,MATHEMATICS,SPORTS,CELEBRITIES,ANIME};
    }

    public enum QuestionCategory{
        GENERAL_KNOWLEDGE,MUSIC,VIDEO_GAMES, COMPUTERS,MATHEMATICS,SPORTS,CELEBRITIES,ANIME,EXIT;

        public static QuestionCategory fromInt(int value){
            return switch (value){
                case 1 -> GENERAL_KNOWLEDGE;
                case 2 -> MUSIC;
                case 3 -> VIDEO_GAMES;
                case 4 -> COMPUTERS;
                case 5 -> MATHEMATICS;
                case 6 -> SPORTS;
                case 7 -> CELEBRITIES;
                case 8 -> ANIME;
                case 0 -> EXIT;
                default -> throw new IllegalArgumentException("Invalid category value: " + value);
            };
        }
    }

    public static class NumbersFact{

        public static final String PATH = "http://numbersapi.com/random";
        public static final String MATH = PATH + "/math?json";
        public static final String TRIVIA = PATH + "/trivia?json";
        public static final String DATE = PATH + "/date?json";
        public static final String YEAR = PATH + "/year?json";

        public static final String[] NUMBERS = {MATH,TRIVIA,DATE,YEAR};
    }

    public enum FactNumber{
        MATH,TRIVIA,DATE,YEAR;

        public static FactNumber fromInt(int value){
            return switch (value){
                case 1 -> MATH;
                case 2 -> TRIVIA;
                case 3 -> DATE;
                case 4 -> YEAR;
                default -> throw new IllegalStateException("Unexpected value: " + value);
            };
        }
    }

    public static class QuotesCon{

        public static final String PATH = "https://api.quotable.io";
        public static final String RANDOM = PATH + "/random";
        public static final String FAMOUS = PATH + "/quotes/random?tags=technology,famous-quotes";
        public static final String HISTORY = PATH + "/quotes/random?tags=history|civil-rights";
        public static final String ALBERT_EINSTEIN = PATH + "/quotes?author=albert-einstein";
        public static final String[] QUOTES = {RANDOM,FAMOUS,HISTORY,ALBERT_EINSTEIN};
    }

    public enum EnumQuotes{
        RANDOM,FAMOUS,HISTORY,ALBERT_EINSTEIN;

        public static EnumQuotes fromInt( int value){
           return switch (value){
                case 1 -> RANDOM;
                case 2 -> FAMOUS;
                case 3 -> HISTORY;
                case 4 -> ALBERT_EINSTEIN;
                default -> throw new IllegalStateException("Unexpected value: " + value);
            };
        }


    }

}
