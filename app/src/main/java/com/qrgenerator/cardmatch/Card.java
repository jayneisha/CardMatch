package com.qrgenerator.cardmatch;

public class Card {
    public final int image;
    public final int faceValue;
    private boolean matched = false;

    public Card(int image,int faceValue) {
        this.image = image;
        this.faceValue = faceValue;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    public boolean isMatched() {
        return matched;
    }
}
