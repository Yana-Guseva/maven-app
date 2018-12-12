package threadpool;

public class SimpleThreadPool {
    public static void main(String[] args) {
        MyThreadPool executorService = new MyThreadPool(8);
//        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread("task " + i);
            executorService.execute(worker);
        }
        executorService.shutdown();
    }
}
