package org.bruno.JsonSerializator;

import com.google.gson.Gson;
import org.junit.*;

public class TestSerializator {

    Gson gson;

    @Before
    public void initialize() {
        this.gson = new Gson();
    }

    //JSON object writer
    //Напишите свой json object writer (object to json string) аналогичный gson на основе javax.json или simple-json и Reflection.
    //Поддержите массивы объектов и примитивных типов, и коллекции из стандартный библиотерки.

    @Test
    public void canSerializePrimitivesAndPrimitiveArrays() throws IllegalAccessException{
        int array[] = {0,10,200,300};
        char array1[] = {'J','A','V','A',' ','C','O','R','E'};
        String array2[] = {"Happy", "New","2019", "Year"};
        Person fr1 = new Person("Petya", 19, null);
        Person friends[] = {fr1};
        Person fr2 = new Person("Katya", 25, friends);
        Person friends2[] = {fr1,fr2};
        Person fr3 = new Person("Povelitel", 29, friends2);
        Person friends3[] = {fr1,fr2,fr3};
        Person fr4 = new Person("Vasilisa Petrovna", 78, friends3);
        TestClass tc = new TestClass(999999999, 999.999, true,"Mefodiy", array, array1, array2, new Address("Jos", fr3));
        TestClass tc1 = new TestClass(95, 19.19, false,"Violetta", array, array1, array2, new Address("Moscow", fr4));
        /*String newtc = gson.toJson(tc);
        System.out.println(newtc);*/
        String toJson = JsonSerializator.serialize(tc).toString();
        String toJson1 = JsonSerializator.serialize(tc1).toString();
        System.out.println(toJson);
        System.out.println(toJson1);
        TestClass fromJSONtc = gson.fromJson(toJson, TestClass.class);
        Assert.assertTrue(tc.equals(fromJSONtc));
        TestClass fromJSONtc1 = gson.fromJson(toJson1, TestClass.class);
        Assert.assertTrue(tc1.equals(fromJSONtc1));
    }
}