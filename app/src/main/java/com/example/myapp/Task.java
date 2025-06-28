package com.example.myapp;

public class Task {
    private String text;
    private boolean completed;

    public Task(String text) {
        this.text = text;
        this.completed = false;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}


