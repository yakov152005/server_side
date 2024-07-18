package org.program;

import static org.program.Constants.Path.PATH;

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
    }

    public static class Text{


        public static final String MAIN_MENU = "What would you like to do? " + "\n" +"""
                1. View tasks
                2. Talk to GPT-4 chat
                3. Get Jokes By category
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
        public static final String ID = "id";
        public static final String TEXT = "text";
        public static final String SUCCESS = "Register success!";
        public static final String LINE = " --> ";
        public static final String USER = ", User: ";

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

}
