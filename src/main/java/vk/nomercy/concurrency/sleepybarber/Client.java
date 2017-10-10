package vk.nomercy.concurrency.sleepybarber;

import lombok.extern.slf4j.Slf4j;
import vk.nomercy.concurrency.Util;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class Client implements Runnable {

    private Random random = new Random();
    private BlockingQueue<Client> queue;
    private boolean served = false;
    private String name = "";

    public Client(BlockingQueue<Client> q, String name) {
        this.queue = q;
        this.name = name;
    }

    @Override
    public void run() {
        try {

            int idleTime = Util.rnd(random, Const.MIN_IDLE_TIME, Const.MAX_IDLE_TIME);
            log.info("Client {}: walking before going to the barber ({} ms).", this, idleTime);
            Thread.sleep(idleTime);

            log.info("Client {}: trying to get in line.", this);
            queue.put(this);

            log.info("Client {}: got in line, now waiting.", this);
            while (!served) {
                Thread.sleep(Const.MIN_HAIRCUT_TIME);
            }
            log.info("Client {}: has been served, now leaving.", this);
        } catch (InterruptedException e) {
            log.error("Oops! Something went wrong.", e);
        }
    }

    public void setServed(boolean value) {
        this.served = value;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
