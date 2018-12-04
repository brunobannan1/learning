package org.bruno.MyTestFramework;

import org.bruno.MyTestFramework.annotations.*;


public class TestClassTest {

    @Before
    public void greetings() {
        System.out.println("Starting test");
    }

    @After
    public void bye() {
        System.out.println("Test complete\n");
    }

    public TestClass instaniate() {
        return new TestClass(9,5);
    }

    @Test
    public void testSetA() {
        TestClass o = instaniate();
        o.setA(100);
        System.out.println( MyTestFramework.assertEquals(o.getA(),100));
    }

    @Test
    public void testSum() {
        TestClass o = instaniate();
        o.sum();
        System.out.println(MyTestFramework.assertEquals(o.getC(),14));
    }

    @Test
    public void testSubstract() {
        TestClass o = instaniate();
        o.substract(100,95);
        System.out.println(MyTestFramework.assertEquals(o.getC(), 5));
    }

    @Test
    public void testSetB() {
        TestClass o = instaniate();
        o.setB(2200);
        System.out.println(MyTestFramework.assertEquals(o.getB(),2200));
    }

    @Test
    public void testDefConstructor() {
        TestClass o = new TestClass();
        System.out.println(MyTestFramework.assertEquals(o.getA(), 0)
                        && MyTestFramework.assertEquals(o.getB(), 0)
                        && MyTestFramework.assertEquals(o.getC(), 10));
    }
}
