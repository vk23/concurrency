package vk.nomercy.concurrency.sleepybarber;

import java.util.concurrent.BlockingQueue;

import vk.nomercy.concurrency.Util;

public class Barber implements Runnable {

	private BlockingQueue<Client> qeueu;

	public Barber(BlockingQueue<Client> q) {
		this.qeueu = q;
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println("Barber: waiting for a client");
				Client c = qeueu.take();
				cut(c);
				// Barber needs some time to get prepared for the next client
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.format("Oops! Something went wrong: %s%n", e.getMessage());
		}
	}

	public synchronized void cut(Client c) throws InterruptedException {
		System.out.format("Barber: cutting client %s%n", c);
		int haircutTime = Util.rnd(Const.MIN_HAIRCUT_TIME, Const.MAX_HAIRCUT_TIME);
		Thread.sleep(haircutTime);
		System.out.format("Barber: cutting client %s done.%n", c);
		c.setServed(true);
	}
}
