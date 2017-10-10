package vk.nomercy.concurrency.sleepybarber;

import lombok.extern.slf4j.Slf4j;
import vk.nomercy.concurrency.Util;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class Barber implements Runnable {

    private BlockingQueue<Client> queue;

    public Barber(BlockingQueue<Client> q) {
        this.queue = q;
    }

    @Override
    public void run() {
        try {
            while (true) {
                log.info("Barber: waiting for a client");
                Client c = queue.take();
                cut(c);
                // Barber needs some time to get prepared for the next client
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            log.error("Oops! Something went wrong.", e);
        }
    }

    public synchronized void cut(Client c) throws InterruptedException {
        log.info("Barber: cutting client {}", c);
        int haircutTime = Util.rnd(Const.MIN_HAIRCUT_TIME, Const.MAX_HAIRCUT_TIME);
        Thread.sleep(haircutTime);
        c.setServed(true);
        log.info("Barber: cutting client {} done.", c);
    }
}
