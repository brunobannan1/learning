package org.bruno.TDDpractice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestAtm {
    Atm atm;
    Card card1;
    Card card2;

    @Before
    public void initialize() {
        this.card1 = new Card("Vasya", 1111_1111_1111_1111L, 100);
        this.card2 = new Card("Petya", 2222_2222_2222_2222L, 10001);
        this.atm = new Atm(new ArrayList<>(Arrays.asList(card1,card2)), 500_000L);
    }

    @Test
    public void atmCacheChangedAfterDeposit() throws IllegalAccessException {
        atm.deposit(1111_1111_1111_1111L,1,1,1);
        Assert.assertTrue(atm.getCache() == 506_100L);
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
}
