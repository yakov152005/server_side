package org.program;

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
    }

    public static class Text{

       public static final String MENU = "What to u want to do ?" + "\n" +
                    """
                    1 - Register to the system
                    2 - Get a list of tasks saved for a specific user
                    3 - Add a task to a specific user
                    4 - Mark a task as done
                    0 - Exit.
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
}
