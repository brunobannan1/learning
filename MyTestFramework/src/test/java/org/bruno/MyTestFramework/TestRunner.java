package org.bruno.MyTestFramework;

public class TestRunner {
    public static void main(String[] args) {
        MyTestFramework myTestFramework = new MyTestFramework();
        myTestFramework.run(TestClassTest.class);
    }
}
