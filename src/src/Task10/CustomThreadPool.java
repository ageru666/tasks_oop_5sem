package Task10;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

class CustomThreadPool {
    private final int poolSize;
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] threads;
    private volatile boolean isShutdown;
    private final Semaphore semaphore;

    public CustomThreadPool(int poolSize) {
        this.poolSize = poolSize;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.threads = new Thread[poolSize];
        this.isShutdown = false;
        this.semaphore = new Semaphore(poolSize);

        for (int i = 0; i < poolSize; i++) {
            threads[i] = new WorkerThread();
            threads[i].start();
        }
    }

    public void execute(Runnable task) throws InterruptedException {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is shutdown");
        }

        try {
            semaphore.acquire();
            taskQueue.add(task);
            synchronized (taskQueue) {
                taskQueue.notify();
            }
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            throw e;
        }
    }

    public void shutdown() {
        isShutdown = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
        synchronized (taskQueue) {
            taskQueue.notifyAll();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task = null;
                    synchronized (taskQueue) {
                        while (taskQueue.isEmpty() && !isShutdown) {
                            try {
                                taskQueue.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }

                        if (isShutdown) {
                            break;
                        }

                        task = taskQueue.poll();
                    }

                    if (task != null) {
                        try {
                            task.run();
                        } finally {
                            semaphore.release();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
