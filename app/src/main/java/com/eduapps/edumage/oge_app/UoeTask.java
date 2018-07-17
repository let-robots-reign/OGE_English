package com.eduapps.edumage.oge_app;

public class UoeTask {
    private int question;
    private int origin;
    private int answer;

    UoeTask(int question, int origin, int answer) {
        this.question = question;
        this.origin = origin;
        this.answer = answer;
    }

    public int getQuestion() {
        return this.question;
    }

    public int getOrigin() {
        return this.origin;
    }

    public int getAnswer() {
        return this.answer;
    }

//    public void setCategory(int category) {
//        this.category = category;
//    }
}
