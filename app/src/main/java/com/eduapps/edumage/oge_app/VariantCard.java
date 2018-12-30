package com.eduapps.edumage.oge_app;

public class VariantCard  {
    private int variantNumber;
    private int result;

    public VariantCard(int number, int res) {
        this.variantNumber = number;
        this.result = res;
    }

    public int getVariantNumber() {
        return variantNumber;
    }

    public int getResult() {
        return result;
    }
}
