package vk.nomercy.concurrency.sleepybarber;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import vk.nomercy.concurrency.Util;

public class Client implements Runnable {

	private Random random = new Random();
	private BlockingQueue<Client> qeueu;
	private boolean served = false;
	private String name = "";

	public Client(BlockingQueue<Client> q, String name) {
		this.qeueu = q;
		this.name = name;
	}

	@Override
	public void run() {
		try {

			int idleTime = Util.rnd(random, Const.MIN_IDLE_TIME, Const.MAX_IDLE_TIME);
			System.out.format("Client %s: walking before going to the barber (%d ms).%n", this, idleTime);
			Thread.sleep(idleTime);

			System.out.format("Client %s: trying to get in line.%n", this);
			qeueu.put(this);

			System.out.format("Client %s: got in line, now waiting.%n", this);
			while (!served) {
				Thread.sleep(Const.MIN_HAIRCUT_TIME);
			}
			System.out.format("Client %s: has been served, now leaving.%n", this);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.format("Oops! Something went wrong: %s%n", e.getMessage());
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
