package org.bruno.JsonSerializator;

import com.google.gson.Gson;
import org.junit.*;

public class TestSerializator {

    Address testAddress;
    Gson gson;
    String jsonObject;

    @Before
    public void initialize() {
        this.testAddress = new Address("Jos");
        this.gson = new Gson();
        this.jsonObject = "";
    }

    //JSON object writer
    //Напишите свой json object writer (object to json string) аналогичный gson на основе javax.json или simple-json и Reflection.
    //Поддержите массивы объектов и примитивных типов, и коллекции из стандартный библиотерки.
    @Test
    public void serializedObjectShouldBeAbleToDeserialize() {
        /*String toJson = JsonSerializator.serialize(testAddress);
        Address testA = gson.fromJson(toJson, Address.class);
        Assert.assertTrue(testA.equals(testAddress));*/
    }

    @Test
    public void canSerializePrimitives() throws IllegalAccessException{
        int array[] = {0,10,200,300};
        TestClass tc = new TestClass(999999999, 999.99, true,"Mefodiy", array);
        /*String newtc = gson.toJson(tc);
        System.out.println(newtc);*/
        String toJson = JsonSerializator.serialize(tc);
        System.out.println(toJson);
        TestClass fromJSONtc = gson.fromJson(toJson, TestClass.class);
        Assert.assertTrue(tc.equals(fromJSONtc));
        System.out.println(tc.toString());
        System.out.println(fromJSONtc.toString());
    }
}