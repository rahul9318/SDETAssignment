package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

    public int getUserId() {
        return userId;
    }

    public boolean isCompleted() {
        return completed;
    }
}
