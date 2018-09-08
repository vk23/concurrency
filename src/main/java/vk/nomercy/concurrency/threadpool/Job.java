package vk.nomercy.concurrency.threadpool;

import vk.nomercy.concurrency.Util;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Job implements Runnable {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private final Random random;
    private final List<Integer> batch;

    private Integer id;

    public Job(Random random, List<Integer> batch) {
        this.random = random;
        this.batch = batch;
        id = counter.incrementAndGet();
    }

    @Override
    public void run() {
        try {
            int delay = Util.rnd(random, 1000, 3000);
            System.out.printf("Starting worker=%d on thread=%d with delay=%d and batch=%n%s%n%n",
                    id,
                    Thread.currentThread().getId(),
                    delay,
                    batch.toString());
            Thread.sleep(delay);
            System.out.printf("Exiting worker=%d%n%n", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
