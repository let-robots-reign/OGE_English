package com.eduapps.edumage.oge_app;

public class UoeTask {
    private String question;
    private String origin;
    private String answer;

    public UoeTask(String question, String origin, String answer) {
        this.question = question;
        this.origin = origin;
        this.answer = answer;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getOrigin() {
        return this.origin;
    }

    public String getAnswer() {
        return this.answer;
    }
}
