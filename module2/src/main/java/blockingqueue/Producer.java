package blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int poisonPill;
    private final int poisonPillPerProducer;

    public Producer(BlockingQueue queue, int poisonPill, int poisonPillPerProducer) {
        this.queue = queue;
        this.poisonPill = poisonPill;
        this.poisonPillPerProducer = poisonPillPerProducer;
    }

    public void run() {
        try {
            generateNumber();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void generateNumber() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            int number = ThreadLocalRandom.current().nextInt(100);
            queue.put(number);
            System.out.println(Thread.currentThread().getName() + " generate number: " + number);
        }
        for (int j = 0; j < poisonPillPerProducer; j++) {
            queue.put(poisonPill);
        }
    }
}
