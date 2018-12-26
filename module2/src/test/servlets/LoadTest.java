package servlets;

import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoadTest {
    private static final String PATH = "http://localhost:8080/test";
    private static final int WORKER_COUNT = 10;

    @Test
    public void loadTest() {
        List<Future<Integer>> results = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(WORKER_COUNT);

        for (int i = 0; i < WORKER_COUNT; i++) {
            results.add(executorService.submit(() -> {
                        HttpURLConnection connection = null;
                        try {
                            System.out.println(Thread.currentThread().getName() + " started to work");
                            URL url = new URL(PATH);
                            connection = (HttpURLConnection) url.openConnection();
                            Thread.sleep(1000);
                            int responseCode = connection.getResponseCode();
                            System.out.println(Thread.currentThread().getName()  + " finished with code " + responseCode);
                            return responseCode;
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            if (connection != null)
                                connection.disconnect();
                        }
                        return null;
                    })
            );
        }

        for (Future<Integer> result : results) {
            try {
                result.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
