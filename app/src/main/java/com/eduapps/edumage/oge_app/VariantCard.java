package com.eduapps.edumage.oge_app;

public class VariantCard  {
    private int variantNumber;
    private boolean hasSolved;

    public VariantCard(int number, boolean hasSolved) {
        this.variantNumber = number;
        this.hasSolved = hasSolved;
    }

    public int getVariantNumber() {
        return variantNumber;
    }

    public boolean getHasSolved() {
        return hasSolved;
    }
}
