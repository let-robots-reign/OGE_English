package com.eduapps.edumage.oge_app;

public class Training {
    private int trainingName;
    private int progress;
    private int iconResource;

    public Training(int name, int progress, int icon) {
        this.trainingName = name;
        this.progress = progress;
        this.iconResource = icon;
    }

    public int getTrainingName() {
        return trainingName;
    }

    public int getProgress() {
        return progress;
    }

    public int getIconResource() {
        return iconResource;
    }
}
