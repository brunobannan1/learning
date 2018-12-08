package org.bruno.TDDpractice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class AtmDepartmentTest {
    AtmDepartment atmDepartment;
    Atm atm1,atm2;
    Card card;

    @Before
    public void initializeTest() {
        this.card = new Card("fake", 1111L, 100_000L);
        this.atm1 = new Atm(new ArrayList<>(Arrays.asList(card)), 1000, 100, 100);
        this.atm2 = new Atm(new ArrayList<>(Arrays.asList(card)), 1, 1, 1);
        this.atmDepartment = new AtmDepartment(new ArrayList<>());
        atmDepartment.addAtm(atm1);
        atmDepartment.addAtm(atm2);
    }

    // • Приложение может содержать несколько ATM
    @Test
    public void departmentCanHaveMoreThanOneAtm () {
        Assert.assertEquals(atmDepartment.getListAtm().get(0), atm1);
        Assert.assertEquals(atmDepartment.getListAtm().get(1), atm2);
    }

    // • Department может собирать сумму остатков со всех ATM
    @Test
    public void departmentCanAcquireSumOfRemainingCacheOfAtms () throws IllegalAccessException {
        long sum = atmDepartment.acquireSum();
        Assert.assertTrue(sum == atm1.getCash() + atm2.getCash());
        atm1.deposit(card.getCardNumber(),10,10,2);
        sum = atmDepartment.acquireSum();
        Assert.assertTrue(sum == atm1.getCash() + atm2.getCash());
        atm2.withdraw(1111,500);
        sum = atmDepartment.acquireSum();
        Assert.assertTrue(sum == atm1.getCash() + atm2.getCash());
        Assert.assertTrue(card.getCash() == 120_500);
    }

    // • Department может инициировать событие – восстановить состояние всех ATM до начального.
    //(начальные состояния у разных ATM могут быть разными)
    @Test
    public void departmentCanResetHisAtmsStateToDefault () throws IllegalAccessException {
        long sum = atmDepartment.acquireSum();
        long beforeAtm1 = atm1.getCash();
        long beforeAtm2 = atm2.getCash();
        atm1.deposit(card.getCardNumber(),10,10,2);
        atm2.withdraw(1111,500);
        Assert.assertTrue(atmDepartment.acquireSum() == sum + 20_500);
        Assert.assertTrue(atm1.getSize100() == 1010);
        Assert.assertTrue(atm1.getSize1000() == 110);
        Assert.assertTrue(atm1.getSize5000() == 102);
        atmDepartment.restoreAtmsState();
        Assert.assertTrue(atm1.getCash() == beforeAtm1);
        Assert.assertTrue(atm1.getSize100() == 1000);
        Assert.assertTrue(atm1.getSize1000() == 100);
        Assert.assertTrue(atm1.getSize5000() == 100);
        Assert.assertTrue(atm2.getCash() == beforeAtm2);
        Assert.assertTrue(atm2.getSize100() == 1);
        Assert.assertTrue(atm2.getSize1000() == 1);
        Assert.assertTrue(atm2.getSize5000() == 1);
        Assert.assertTrue(atmDepartment.acquireSum() == atm1.getCash() + atm2.getCash());
    }
}