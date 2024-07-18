package org.program;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private boolean success;
    private Integer errorCode;
    private String extra;
    private List<TaskModel> tasks;


    public Response(boolean success,Integer errorCode, String extra,List<TaskModel> tasks){
        this.success = success;
        this.errorCode = errorCode;
        this.extra = extra;
        this.tasks = tasks;
    }

    public Response(){

    }
    public boolean isSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public List<TaskModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskModel> tasks) {
        this.tasks = tasks;
    }

}
