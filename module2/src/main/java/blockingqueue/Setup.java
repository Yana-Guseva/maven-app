package blockingqueue;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Setup {
    private static int N_PRODUCERS = 4;
    private static int N_CONSUMERS = 1;
    private static int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;
    private static int mod = N_CONSUMERS % N_PRODUCERS;

    public static void main(String[] args) {
        int BOUND = 10;
        ArrayList<Thread> threads = new ArrayList<>(N_PRODUCERS);
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BOUND);
        for (int i = 0; i < N_PRODUCERS; i++) {
            Thread thread = new Thread(new Producer(queue, Integer.MAX_VALUE, poisonPillPerProducer));
            threads.add(thread);
            thread.start();
        }

        for (int j = 0; j < N_CONSUMERS; j++) {
            new Thread(new Consumer(queue, Integer.MAX_VALUE)).start();
        }

        try {
            for (int k = 0; k < threads.size() - 1; k++) {
                threads.get(k).join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int l = 0; l < mod; l++) {
            queue.add(Integer.MAX_VALUE);
        }
    }
}
