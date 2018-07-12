package com.eduapps.edumage.oge_app;

public class TheoryCard {
    private int cardName;
    private int iconResourse;
    private int cardsWatched;
    private int cardsTotal;

    public TheoryCard(int cardName, int iconResourse, int cardsWatched, int cardsTotal) {
        this.cardName = cardName;
        this.iconResourse = iconResourse;
        this.cardsWatched = cardsWatched;
        this.cardsTotal = cardsTotal;
    }

    public int getCardName() {
        return cardName;
    }

    public int getIconResourse() {
        return iconResourse;
    }

    public int getCardsWatched() {
        return cardsWatched;
    }

    public int getCardsTotal() {
        return cardsTotal;
    }
}
