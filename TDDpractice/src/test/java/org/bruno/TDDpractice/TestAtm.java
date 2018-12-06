package org.bruno.TDDpractice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CancellationException;

public class TestAtm {
    Atm atm;
    Card card1;
    Card card2;
    Card card3;

    @Before
    public void initialize() {
        this.card1 = new Card("Vasya", 1111_1111_1111_1111L, 100);
        this.card2 = new Card("Petya", 2222_2222_2222_2222L, 10001);
        this.card3 = new Card("Mongol", 3333_3333_3333_3333L, 100_000);
        // 700_000 RUB
        this.atm = new Atm(new ArrayList<>(Arrays.asList(card1,card2,card3)), 1000, 100, 100);
    }

    @Test
    public void atmCacheChangedAfterDeposit() throws IllegalAccessException {
        int bef100 = atm.getSize100();
        int bef1000 = atm.getSize1000();
        int bef5000 = atm.getSize5000();
        // 25_000 RUB
        atm.deposit(1111_1111_1111_1111L,100,10,1);
        Assert.assertTrue(atm.getSize100() == bef100 + 100);
        Assert.assertTrue(atm.getSize1000() == bef1000 + 10);
        Assert.assertTrue(atm.getSize5000() == bef5000 + 1);
        Assert.assertTrue(atm.getCache() == 725_000L);
    }

    @Test
    public void cardCacheChangedAfterDeposit() throws IllegalAccessException {
        atm.deposit(2222_2222_2222_2222L,10,10,5);
        Assert.assertTrue(card2.getCache() == 46_001L);
    }

    @Test
    public void canDepositInDifferentNominal() throws IllegalAccessException {
        long beforeAtmCache = atm.getCache();
        atm.deposit(1111_1111_1111_1111L,1,1,1);
        long afterAtmCache = atm.getCache();
        Assert.assertTrue(beforeAtmCache + 6100L == afterAtmCache);
        //Assert.assertTrue();
    }

    @Test(expected = CancellationException.class)
    public void canWithdrawCache() throws CancellationException, IllegalAccessException {
        int atmSize100 = atm.getSize100();
        int atmSize5000 = atm.getSize5000();
        long before = card1.getCache();
        long before2 = card2.getCache();

        atm.withdraw(card1.getCardNumber(), 100);
        Assert.assertTrue(card1.getCache() == before - 100);
        Assert.assertTrue(atm.getSize100() == atmSize100 - 1);

        atm.withdraw(card2.getCardNumber(), 5_000);
        Assert.assertTrue(card2.getCache() == before2 - 5_000);
        Assert.assertTrue(atm.getSize5000() == atmSize5000 - 1);

        atm.withdraw(card2.getCardNumber(), 5_000);
        Assert.assertTrue(card2.getCache() == before2 - 10_000);
        Assert.assertTrue(atm.getSize5000() == atmSize5000 - 2);

        atm.withdraw(card1.getCardNumber(), 100);
    }

    @Test
    public void card3Withdraw() throws CancellationException, IllegalAccessException {
        int atmSize100 = atm.getSize100();
        int atmSize1000 = atm.getSize1000();
        int atmSize5000 = atm.getSize5000();
        long before = card3.getCache();
        long atmBefore = atm.getCache();
        atm.withdraw(card3.getCardNumber(), 73_400);
        Assert.assertTrue(atm.getCache() == atmBefore - 73_400);
        Assert.assertTrue(atm.getSize5000() == atmSize5000 - 14);
        Assert.assertTrue(atm.getSize1000() == atmSize1000 - 3);
        Assert.assertTrue(atm.getSize100() == atmSize100 - 4);
    }

    @Test
    public void canCheckCacheLeft() throws IllegalAccessException {
        long left = atm.cacheLeft(card1.getCardNumber());
        Assert.assertTrue(left == card1.getCache());
    }
}
