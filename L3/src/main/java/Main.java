import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        MyListImplementation<String> list = new MyListImplementation();
        list.add(0,"test");
        list.add(1,"worked");
        list.add(2,"no");
        list.add(3,"yes?");
        //        list.add(10,"desyat");
        list.add("last");
        for (String str : list) {
            System.out.println(str);
        }
    }
}
