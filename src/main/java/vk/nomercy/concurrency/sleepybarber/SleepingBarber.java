package vk.nomercy.concurrency.sleepybarber;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SleepingBarber {

	public void start(int numClients) {

		BlockingQueue<Client> q = new ArrayBlockingQueue<>(Const.MAX_CLIENTS);

		// barber
		Barber barber = new Barber(q);
		new Thread(barber).start();

		// clients
		for (int i = 1; i <= numClients; i++) {
			Client c = new Client(q, "Client #" + i);
			new Thread(c).start();
		}
	}
}
