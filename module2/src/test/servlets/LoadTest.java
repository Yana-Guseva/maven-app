package servlets;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadTest {
    private static final String PATH = "http://localhost:8080/test?number=";
    private static final int WORKER_COUNT = 5;

    @Test
    public void loadTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(WORKER_COUNT);

        for (int i = 0; i < WORKER_COUNT; i++) {
            int param = i;
            executorService.submit(() -> {
                try {
                    sendGet(param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    private void sendGet(int param) throws Exception {
        HttpURLConnection connection = null;
        URL url = new URL(PATH + param);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

//        Thread.sleep(10);

        System.out.println("Sending request to URL : " + url);
        System.out.println("Response Code : " + connection.getResponseCode());

        connection.disconnect();
    }
}
