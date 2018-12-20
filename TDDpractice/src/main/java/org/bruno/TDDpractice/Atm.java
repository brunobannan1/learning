package org.bruno.TDDpractice;

import org.bruno.TDDpractice.interfaces.AtmListenerInterface;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;

public class Atm implements AtmListenerInterface {

    private ArrayList<Card> cards;
    private long cash;
    private int size100, size1000, size5000;
    private AtmSnapshot atmSnapshot;

    public Atm(ArrayList<Card> cards, int size100, int size1000, int size5000) {
        this.cards = cards;
        this.size100 = size100;
        this.size1000 = size1000;
        this.size5000 = size5000;
        this.cash = size100 * 100 +
                size1000 * 1000 +
                size5000 * 5000;
        atmSnapshot = makeSnapshot();
    }

    @Override
    public String toString() {
        return "Atm{" +
                "cash=" + cash +
                ", size100=" + size100 +
                ", size1000=" + size1000 +
                ", size5000=" + size5000 +
                '}';
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public int getSize100() {
        return size100;
    }

    public void setSize100(int size100) {
        this.size100 = size100;
    }

    public int getSize1000() {
        return size1000;
    }

    public void setSize1000(int size1000) {
        this.size1000 = size1000;
    }

    public int getSize5000() {
        return size5000;
    }

    public void setSize5000(int size5000) {
        this.size5000 = size5000;
    }

    public void deposit(long cardNumber, int rub100, int rub1000, int rub5000) throws IllegalAccessException {
        long money = rub100 * 100 + rub1000 * 1000 + rub5000 * 5000;
        Card card = getCardByCardNumber(cardNumber);
        this.size100 = this.size100 + rub100;
        this.size1000 = this.size1000 + rub1000;
        this.size5000 = this.size5000 + rub5000;
        this.cash = this.cash + money;
        card.setCash(card.getCash() + money);
    }

    public void withdraw(long cardNumber, long cash) throws IllegalAccessException, CancellationException {
        Card card = getCardByCardNumber(cardNumber);
        if (this.getCash() < cash) throw new CancellationException("Sorry, ATM has no money.");
        if (card.getCash() < cash)
            throw new CancellationException("Sorry, Insufficient funds. Cash left: " + card.getCash());
        boolean can = canWithdraw(cash);
        if (can) {
            card.setCash(card.getCash() - cash);
        } else throw new CancellationException("Sorry, ATM cant give this cash: " + cash);
    }

    public long cashLeft(long cardNumber) throws IllegalAccessException {
        Card card = getCardByCardNumber(cardNumber);
        return card.getCash();
    }

    public AtmSnapshot makeSnapshot() {
        return new AtmSnapshot(this, cards, cash, size100, size1000, size5000);
    }

    public void restoreFromSnapshot() {
        atmSnapshot.restore();
    }

    private boolean canWithdraw(long cash) {
        int i100 = this.getSize100();
        int i1000 = this.getSize1000();
        int i5000 = this.getSize5000();
        if (cash == 0) return false;
        long left = cash;
        long minus5000 = 0;
        long minus1000 = 0;
        if (i5000 > 0) {
            minus5000 = cash / 5000;
            if (minus5000 > i5000) {
                int dif = (int) (minus5000 - i5000);
                left = (cash % 5000) + dif * 5_000;
                minus5000 = minus5000 - dif;
            } else {
                left = cash % 5000;
            }
        }
        if (i1000 > 0) {
            minus1000 = left / 1000;
            if (minus1000 > i1000) {
                int dif = (int) (minus1000 - i1000);
                left = (left % 1000) + dif * 1_000;
                minus1000 = minus1000 - dif;
            } else {
                left = left % 1000;
            }
        }
        if (i100 > 0) {
            long minus100 = left / 100;
            left = left % 100;
            if (left == 0) {
                this.setSize100(this.getSize100() - (int) minus100);
                this.setSize1000(this.getSize1000() - (int) minus1000);
                this.setSize5000(this.getSize5000() - (int) minus5000);
                this.setCash(this.getCash() - cash);
                return true;
            }
        }
        return false;
    }

    private Card getCardByCardNumber(long cardNumber) throws IllegalAccessException {
        for (Card card : cards) {
            if (cardNumber == card.getCardNumber()) return card;
        }
        throw new IllegalAccessException();
    }

    @Override
    public void notificate() {
        restoreFromSnapshot();
    }

    public class AtmSnapshot {
        private final Atm atm;
        private final ArrayList<Card> cards;
        private final long cash;
        private final int size100, size1000, size5000;

        public AtmSnapshot(Atm atm, ArrayList<Card> cards, long cash, int size100, int size1000, int size5000) {
            this.atm = atm;
            this.cards = cards;
            this.cash = cash;
            this.size100 = size100;
            this.size1000 = size1000;
            this.size5000 = size5000;
        }

        public void restore() {
            atm.setSize100(size100);
            atm.setSize1000(size1000);
            atm.setSize5000(size5000);
            atm.setCards(cards);
            atm.setCash(cash);
        }
    }
}