import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        int size = 20_000_000;

        System.out.println("Starting the loop");
        while (true) {
            System.gc();
            Thread.sleep(10);
            Runtime runtime = Runtime.getRuntime();
            long mem = runtime.totalMemory() - runtime.freeMemory();
            System.out.println(mem);

            Object[] objects = new Object[size];

            long mem2 = runtime.totalMemory() - runtime.freeMemory();
            System.out.println((mem2 - mem)/size);

            System.out.println("New array of size: " + objects.length + " created");
            for (int i = 0; i < size; i++ ) {
                objects[i] = new Object();
            }
            System.out.println("Created " + size + " objects");
            Thread.sleep(1000);
        }

    }
}
