package Task7;

import java.util.concurrent.atomic.AtomicInteger;

class CustomCyclicBarrier {
    private final int parties;
    private AtomicInteger count = new AtomicInteger(0);
    private AtomicInteger generation = new AtomicInteger(0);

    public CustomCyclicBarrier(int parties) {
        this.parties = parties;
    }

    public void await() throws InterruptedException {
        synchronized (this) {
            int currentGeneration = generation.get();

            count.incrementAndGet();
            int currentCount = count.get();

            if (currentCount < parties) {
                while (generation.get() == currentGeneration) {
                    wait();
                }
            } else {
                count.set(0);
                generation.incrementAndGet();
                notifyAll();
            }
        }
    }
}
