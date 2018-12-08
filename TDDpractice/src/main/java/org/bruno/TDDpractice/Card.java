package org.bruno.TDDpractice;

public class Card {
    private String cardholder;
    private long cash;
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

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public Card(String cardholder, long cardNumber, long cash) {
        this.cardholder = cardholder;
        this.cardNumber = cardNumber;
        this.cash = cash;
    }

}