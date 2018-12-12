package org.bruno.JsonSerializator;

import java.util.Arrays;
import java.util.Objects;

public class TestClass {
    private int size;
    private double dB;
    public boolean canMakeTests;
    private String name;
    private int[] arrayInt;
    private char[] arrayChar;
    private String[] arrayString;
    private Address classAddress;

    public TestClass(int size, double dB, boolean canMakeTests, String name, int[] arrayInt, char[] arrayChar, String[] arrayString, Address classAddress) {
        this.size = size;
        this.dB = dB;
        this.canMakeTests = canMakeTests;
        this.name = name;
        this.arrayInt = arrayInt;
        this.arrayChar = arrayChar;
        this.arrayString = arrayString;
        this.classAddress = classAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClass testClass = (TestClass) o;
        return size == testClass.size &&
                Double.compare(testClass.dB, dB) == 0 &&
                canMakeTests == testClass.canMakeTests &&
                Objects.equals(name, testClass.name) &&
                Arrays.equals(arrayInt, testClass.arrayInt) &&
                Arrays.equals(arrayChar, testClass.arrayChar) &&
                Arrays.equals(arrayString, testClass.arrayString) &&
                Objects.equals(classAddress, testClass.classAddress);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size, dB, canMakeTests, name, classAddress);
        result = 31 * result + Arrays.hashCode(arrayInt);
        result = 31 * result + Arrays.hashCode(arrayChar);
        result = 31 * result + Arrays.hashCode(arrayString);
        return result;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "size=" + size +
                ", dB=" + dB +
                ", canMakeTests=" + canMakeTests +
                ", name='" + name + '\'' +
                ", arrayInt=" + Arrays.toString(arrayInt) +
                ", arrayChar=" + Arrays.toString(arrayChar) +
                ", arrayString=" + Arrays.toString(arrayString) +
                ", classAddress=" + classAddress +
                '}';
    }
}