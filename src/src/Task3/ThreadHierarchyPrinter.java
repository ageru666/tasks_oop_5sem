package Task3;

import java.util.concurrent.TimeUnit;

public class ThreadHierarchyPrinter {
    public static void printThreadHierarchy(ThreadGroup group, int level) {
        System.out.println("".repeat(level * 4) + "Група: " + group.getName() + ", активні потоки: " + group.activeCount());

        Thread[] threads = new Thread[group.activeCount()];
        group.enumerate(threads);

        for (Thread thread : threads) {
            System.out.println("".repeat((level + 1) * 4) + "Потік: " + thread.getName() + ", стан: " + thread.getState());
        }

        int groupCount = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[groupCount];
        group.enumerate(groups);

        for (ThreadGroup subgroup : groups) {
            printThreadHierarchy(subgroup, level + 1);
        }
    }

    public static void main(String[] args) {
        ThreadGroup mainGroup = new ThreadGroup("Головна група");

        Thread thread1 = new Thread(mainGroup, () -> {
            int i = 0;
            while (i < 200000) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    i += 50000;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(mainGroup, () -> {
            while (true) {

            }
        });
        thread2.start();

        ThreadGroup subGroup = new ThreadGroup(mainGroup, "Підгрупа");
        Thread thread3 = new Thread(subGroup, () -> {
            int i = 0;
            while (i < 400000) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    i += 50000;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread3.start();

        Thread hierarchyPrinterThread = new Thread(() -> {
            while (true) {
                printThreadHierarchy(mainGroup, 0);
                System.out.println("");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        hierarchyPrinterThread.start();
    }
}

