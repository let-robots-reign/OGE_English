package com.eduapps.edumage.oge_app;

public class ActivityItem {
    private String topicName;
    private int expCollected;
    private int rightAnswers;
    private int totalPoints;
    private int dynamics;

    ActivityItem (String name, int exp, int right, int total, int dynamics) {
        this.topicName = name;
        this.expCollected = exp;
        this.rightAnswers = right;
        this.totalPoints = total;
        this.dynamics = dynamics;
    }

    public String getTopicName() {
        return topicName;
    }

    public int getExpCollected() {
        return expCollected;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getDynamics() {
        return dynamics;
    }
}
