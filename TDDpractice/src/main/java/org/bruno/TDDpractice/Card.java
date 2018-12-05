package org.bruno.TDDpractice;

public class Card {
    private String cardholder;
    private long cache;
    public long cardNumber;


    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getCache() {
        return cache;
    }

    public void setCache(long cache) {
        this.cache = cache;
    }

    public Card(String cardholder, long cardNumber, long cache) {
        this.cardholder = cardholder;
        this.cardNumber = cardNumber;
        this.cache = cache;
    }

}
