package Task9;

import java.util.concurrent.atomic.AtomicInteger;

public class Phaser {
    private final AtomicInteger parties = new AtomicInteger();
    private final AtomicInteger awaiting = new AtomicInteger();
    private final AtomicInteger phase = new AtomicInteger(0);
    private boolean isTerminated = false;


    public Phaser(int parties) {
        this.parties.set(parties);
        this.awaiting.set(parties);
    }

    public synchronized int register() {
        this.parties.incrementAndGet();
        this.awaiting.incrementAndGet();
        return this.phase.get();
    }

    public synchronized int arrive() {
        if (this.awaiting.decrementAndGet() == 0) {
            nextPhase();
        }
        return this.phase.get();
    }

    public synchronized int arriveAndDeregister() {
        this.parties.decrementAndGet();
        if (this.awaiting.decrementAndGet() == 0) {
            nextPhase();
        }
        return this.phase.get();
    }

    public synchronized int arriveAndAwaitAdvance() throws InterruptedException {
        if (this.awaiting.decrementAndGet() > 0) {
            this.wait();
        } else {
            nextPhase();
        }
        return this.phase.get();
    }

    private void nextPhase() {
        this.awaiting.set(this.parties.get());
        if (!isTerminated) {
            this.phase.incrementAndGet();
        }
        notifyAll();
    }

    public int getPhase() {
        return this.phase.get();
    }
}