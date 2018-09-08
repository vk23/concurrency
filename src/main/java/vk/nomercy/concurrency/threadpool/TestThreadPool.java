package vk.nomercy.concurrency.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {

    private static final int TOTAL = 100;
    private static final int BATCH_SIZE = 7;
    private static final int THREAD_NUM = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
//        useExecutorService();
        useCompletableFutures();
    }

    private static void useExecutorService() {
        System.out.println("start");

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
        jobs().forEach(executorService::submit);

        try {
            boolean b = executorService.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println("await=" + b);
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    private static void useCompletableFutures() {
        System.out.println("start");

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);

        CompletableFuture.allOf(jobs().stream()
                .map(r -> CompletableFuture.runAsync(r, executorService))
                .toArray(CompletableFuture[]::new))
                .thenAccept((v) -> {
                    System.out.println("all done");
                    executorService.shutdownNow();
                });

        System.out.println("end");
    }

    private static ArrayList<Runnable> jobs() {
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < TOTAL; i++) {
            ints.add(i);
        }

        ArrayList<Runnable> jobs = new ArrayList<>();
        int index = 0;
        while (index < TOTAL) {
            int next = nextIndex(index);
            List<Integer> batch = ints.subList(index, next);
            jobs.add(new Job(new Random(), batch));
            index = next;
        }
        return jobs;
    }

    private static int nextIndex(int currentIndex) {
        return currentIndex + BATCH_SIZE <= TOTAL
                ? currentIndex + BATCH_SIZE
                : TOTAL;
    }


}
