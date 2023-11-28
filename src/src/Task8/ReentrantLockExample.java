package Task8;

public class ReentrantLockExample {

    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockCount = 0;
    private int holdCount = 0;
    public synchronized void lock() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        while (isLocked && lockedBy != currentThread) {
            wait();
        }
        isLocked = true;
        lockedBy = currentThread;
        lockCount++;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == lockedBy) {
            lockCount--;

            if (lockCount == 0) {
                isLocked = false;
                lockedBy = null;
                notify();
            }
        }
    }
    public synchronized boolean tryLock() {
        if (!isLocked) {
            isLocked = true;
            lockedBy = Thread.currentThread();
            lockCount++;
            holdCount = 1;
            return true;
        } else if (lockedBy == Thread.currentThread()) {
            lockCount++;
            holdCount++;
            return true;
        }
        return false;
    }

    public synchronized boolean tryLock(long timeout) throws InterruptedException {
        long endTime = System.currentTimeMillis() + timeout;
        long remainingTime = timeout;

        while (isLocked && lockedBy != Thread.currentThread() && remainingTime > 0) {
            wait(remainingTime);
            remainingTime = endTime - System.currentTimeMillis();
        }

        if (!isLocked) {
            isLocked = true;
            lockedBy = Thread.currentThread();
            lockCount++;
            holdCount = 1;
            return true;
        } else if (lockedBy == Thread.currentThread()) {
            lockCount++;
            holdCount++;
            return true;
        }

        return false;
    }

    public synchronized int getHoldCount() {
        if (isHeldByCurrentThread()) {
            return holdCount;
        } else {
            return 0;
        }
    }

    public synchronized boolean isHeldByCurrentThread() {
        return isLocked && lockedBy == Thread.currentThread();
    }
}

