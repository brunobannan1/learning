package org.bruno.TDDpractice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CancellationException;

public class TestAtm {
    Atm atm, atm2;
    Card card1;
    Card card2;
    Card card3;

    @Before
    public void initialize() {
        this.card1 = new Card("Vasya", 1111_1111_1111_1111L, 600_000);
        this.card2 = new Card("Petya", 2222_2222_2222_2222L, 10001);
        this.card3 = new Card("Mongol", 3333_3333_3333_3333L, 100_000);
        // 700_000 RUB
        this.atm = new Atm(new ArrayList<>(Arrays.asList(card1, card2, card3)), 1000, 100, 100);
        this.atm2 = new Atm(new ArrayList<>(Arrays.asList(card1, card2, card3)), 1000, 1, 1);
    }

    @Test
    public void atmCashChangedAfterDeposit() throws IllegalAccessException {
        int bef100 = atm.getSize100();
        int bef1000 = atm.getSize1000();
        int bef5000 = atm.getSize5000();
        // 25_000 RUB
        atm.deposit(1111_1111_1111_1111L, 100, 10, 1);
        Assert.assertTrue(atm.getSize100() == bef100 + 100);
        Assert.assertTrue(atm.getSize1000() == bef1000 + 10);
        Assert.assertTrue(atm.getSize5000() == bef5000 + 1);
        Assert.assertTrue(atm.getCash() == 725_000L);
    }

    @Test
    public void cardCashChangedAfterDeposit() throws IllegalAccessException {
        atm.deposit(2222_2222_2222_2222L, 10, 10, 5);
        Assert.assertTrue(card2.getCash() == 46_001L);
    }

    @Test
    public void canDepositInDifferentNominal() throws IllegalAccessException {
        long beforeAtmCash = atm.getCash();
        long cardCash = card1.getCash();
        atm.deposit(1111_1111_1111_1111L, 1, 1, 1);
        long afterAtmCash = atm.getCash();
        Assert.assertTrue(beforeAtmCash + 6100L == afterAtmCash);
        Assert.assertTrue(card1.getCash() == cardCash + 6100L);
    }

    @Test(expected = CancellationException.class)
    public void canWithdrawCash() throws CancellationException, IllegalAccessException {
        int atmSize100 = atm.getSize100();
        int atmSize5000 = atm.getSize5000();
        long before = card1.getCash();
        long before2 = card2.getCash();

        atm.withdraw(card1.getCardNumber(), 100);
        Assert.assertTrue(card1.getCash() == before - 100);
        Assert.assertTrue(atm.getSize100() == atmSize100 - 1);

        atm.withdraw(card2.getCardNumber(), 5_000);
        Assert.assertTrue(card2.getCash() == before2 - 5_000);
        Assert.assertTrue(atm.getSize5000() == atmSize5000 - 1);

        atm.withdraw(card2.getCardNumber(), 5_000);
        Assert.assertTrue(card2.getCash() == before2 - 10_000);
        Assert.assertTrue(atm.getSize5000() == atmSize5000 - 2);

        atm.withdraw(card1.getCardNumber(), 10_000_000);
    }

    @Test
    public void card3Withdraw() throws CancellationException, IllegalAccessException {
        int atmSize100 = atm.getSize100();
        int atmSize1000 = atm.getSize1000();
        int atmSize5000 = atm.getSize5000();
        long before = card3.getCash();
        long atmBefore = atm.getCash();
        atm.withdraw(card3.getCardNumber(), 73_400);
        Assert.assertTrue(atm.getCash() == atmBefore - 73_400);
        Assert.assertTrue(atm.getSize5000() == atmSize5000 - 14);
        Assert.assertTrue(atm.getSize1000() == atmSize1000 - 3);
        Assert.assertTrue(atm.getSize100() == atmSize100 - 4);
    }

    @Test
    public void card1Withdraw() throws CancellationException, IllegalAccessException {
        int atmSize100 = atm.getSize100();
        int atmSize1000 = atm.getSize1000();
        int atmSize5000 = atm.getSize5000();
        long before = card1.getCash();
        long atmBefore = atm.getCash();
        atm.withdraw(card1.getCardNumber(), 506_000);
        Assert.assertTrue(atm.getCash() == atmBefore - 506_000);
        Assert.assertTrue(atm.getSize5000() == atmSize5000 - 100);
        Assert.assertTrue(atm.getSize1000() == atmSize1000 - 6);
        Assert.assertTrue(atm.getSize100() == atmSize100);
        Assert.assertTrue(card1.getCash() == before - 506_000);
    }

    @Test
    public void atm2test() throws CancellationException, IllegalAccessException {
        int atmSize100 = atm2.getSize100();
        int atmSize1000 = atm2.getSize1000();
        int atmSize5000 = atm2.getSize5000();
        long before = card3.getCash();
        long atmBefore = atm2.getCash();
        atm2.withdraw(card3.getCardNumber(), 100_000);
        Assert.assertTrue(atm2.getCash() == atmBefore - 100_000);
        Assert.assertTrue(atm2.getSize5000() == atmSize5000 - 1);
        Assert.assertTrue(atm2.getSize1000() == atmSize1000 - 1);
        Assert.assertTrue(atm2.getSize100() == atmSize100 - 940);
        Assert.assertTrue(card3.getCash() == before - 100_000);
    }

    @Test
    public void canCheckCashLeft() throws IllegalAccessException {
        long left = atm.cashLeft(card1.getCardNumber());
        Assert.assertTrue(left == card1.getCash());
    }
}