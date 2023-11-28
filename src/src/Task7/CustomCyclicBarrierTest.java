package Task7;


import org.junit.Test;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomCyclicBarrierTest {

    private static final int numThreads = 5;

    @Test
    public void testCustomCyclicBarrier() throws InterruptedException {
        CustomCyclicBarrier barrier = new CustomCyclicBarrier(numThreads);

        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    testMethod(barrier);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

           Thread.sleep(5000);
    }
    private static final AtomicInteger waitTime = new AtomicInteger(0);

    public static void testMethod(CustomCyclicBarrier barrier) throws InterruptedException {
        Random random = new Random();
        int randomWaitTime = random.nextInt(1000);

        long before = System.currentTimeMillis();
        System.out.println("Thread " + Thread.currentThread().getName() + " is waiting for " + randomWaitTime + " ms before the barrier");
        Thread.sleep(waitTime.getAndAccumulate(randomWaitTime, Integer::sum));

        barrier.await();

        long after = System.currentTimeMillis();
        System.out.println("Thread " + Thread.currentThread().getName() + " has crossed the barrier, waited: " + (after - before) + " ms");
    }
}
