package com.eduapps.edumage.oge_app;

public class UoeTask {
    private int id;
    private String question;
    private String origin;
    private String answer;
    private int completion;

    public UoeTask(int id, String question, String origin, String answer, int completion) {
        this.id = id;
        this.question = question;
        this.origin = origin;
        this.answer = answer;
        this.completion = completion;
    }

    public int getId() {
        return id;
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

    public int getCompletion() {
        return completion;
    }
}
