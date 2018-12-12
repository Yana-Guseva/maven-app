package threadpool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class MyThreadPool implements Executor {
    private final Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
    private volatile boolean isRunning = true;

    public MyThreadPool(int threadNum) {
        for (int i = 0; i < threadNum; i++) {
            new Thread(new TaskWorker()).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            workQueue.offer(command);
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    private final class TaskWorker implements Runnable {
        @Override
        public void run() {
            while (isRunning || !workQueue.isEmpty()) {
                Runnable task = workQueue.poll();
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}
