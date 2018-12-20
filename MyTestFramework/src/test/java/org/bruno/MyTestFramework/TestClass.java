package org.bruno.MyTestFramework;

public class TestClass {

    public int c;
    private int a;
    private int b;


    public TestClass() {
        this.a = 0;
        this.b = 0;
        this.c = 10;
    }

    public TestClass(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int sum() {
        c = a + b;
        return c;
    }

    public int substract(int b, int a) {
        c = b - a;
        return c;
    }

    public int getC() {
        return c;
    }

    public void Print() {
        System.out.println(this.toString());
    }
}