package org.bruno.TDDpractice;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;

public class Atm {

    private ArrayList<Card> cards;
    private long cache;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setCache(long cache) {
        this.cache = cache;
    }

    public long getCache() {
        return cache;
    }

    public Atm(ArrayList<Card> cards, long cache) {
        this.cards = cards;
        this.cache = cache;
    }

    public void deposit(long cardNumber, int rub100, int rub1000, int rub5000) throws IllegalAccessException, CancellationException {
        long money = rub100 * 100 + rub1000 * 1000 + rub5000 * 5000;
        if (this.cache < money ) throw new CancellationException();
        Card card = getCardByCardNumber(cardNumber);
        this.cache = this.cache + money;
        card.setCache(card.getCache() + money);
    }

    private Card getCardByCardNumber(long cardNumber) throws IllegalAccessException {
        for (Card card : cards) {
            if (cardNumber == card.getCardNumber()) return card;
        }
        throw new IllegalAccessException();
    }
}
