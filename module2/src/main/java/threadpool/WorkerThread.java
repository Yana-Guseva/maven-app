package threadpool;

public class WorkerThread implements Runnable {

    private String task;

    public WorkerThread(String task) {
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started " + task);
        process();
        System.out.println(Thread.currentThread().getName()  + " finished " + task);
    }

    private void process() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
