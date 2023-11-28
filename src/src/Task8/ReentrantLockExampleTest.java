package Task8;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class ReentrantLockExampleTest {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    @Test
    public void testReentrantLockExample() throws InterruptedException {
        ReentrantLockExample reentrantLock = new ReentrantLockExample();
        int numThreads = 4;

        for (int i = 0; i < numThreads; i++) {
            int threadNumber = i;
            Thread thread = new Thread(() -> {
                try {
                    System.out.println("Thread-" + threadNumber);
                    System.out.println("Start time: " + sdf.format(new Date()));

                    // Try to acquire the lock, with a timeout of 1000 milliseconds
                    boolean lockAcquired = reentrantLock.tryLock(1000);

                    if (lockAcquired) {
                        System.out.println("Lock acquired by Thread-" + threadNumber + " with hold count: " +
                                reentrantLock.getHoldCount());

                        // Perform some work while holding the lock
                        Thread.sleep(2000);

                        System.out.println("End time: " + sdf.format(new Date()));
                    } else {
                        System.out.println("Thread-" + threadNumber + " failed to acquire the lock within the timeout.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            });

            thread.start();

            thread.join();
        }
    }
}
