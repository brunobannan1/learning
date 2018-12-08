package org.bruno.TDDpractice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TestObservable {

    AtmDepartment atmDepartment;
    Atm atm1,atm2;
    Card card;
    Random rnd;
    int totalCount = 25;

    @Before
    public void initializeTest() {
        this.rnd = new Random();
        this.card = new Card("fake", 1111L, 100_000L);
        this.atm1 = new Atm(new ArrayList<>(Arrays.asList(card)), 1000, 100, 100);
        this.atm2 = new Atm(new ArrayList<>(Arrays.asList(card)), 1, 1, 1);
        this.atmDepartment = new AtmDepartment(new ArrayList<>());
        atmDepartment.addAtm(atm1);
        atmDepartment.addAtm(atm2);
    }

    @Test
    public void publisherShouldNotificateObsers() throws IllegalAccessException {
        int testCount = totalCount;
        while (testCount > 0) {
            System.out.println("Test # " + (totalCount - testCount + 1) + " started");
            boolean state = rnd.nextBoolean();
            if(state) {
                System.out.println("ATM 1 state before: " + atm1.toString());
                long beforeAtm1 = atm1.getCash();
                atm1.getSize100();
                atm1.getSize1000();
                atm1.getSize1000();
                int random = (rnd.nextInt(99)+1) * 1_000;
                atm1.withdraw(1111, random);
                System.out.println("Random value was: " + random);
                System.out.println("ATM 1 state was changed to: " + atm1.toString());
                Assert.assertTrue(atm1.getCash() == beforeAtm1 - random);
                atmDepartment.notificate();
                Assert.assertTrue(atm1.getCash() == beforeAtm1);
                atm1.deposit(1111,random / 100,0,0);
                atmDepartment.notificate();
            }
            testCount--;
            System.out.println("Nothing changed in ATM 1 state: " + atm1.toString());
            System.out.println("¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤-¤");
        }
    }
}
