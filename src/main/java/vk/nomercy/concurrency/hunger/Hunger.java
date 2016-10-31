package vk.nomercy.concurrency.hunger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.time.StopWatch;

public class Hunger implements Runnable {

	private List<Employee> employees = new ArrayList<>();
	private List<Manager> managers = new ArrayList<>();
	private StatsTimer statsTimer;
	private Timer timer;
	private boolean active = true;

	public void startGame() {
		System.out.println("Hunger starting");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		startCliAndTimer();
		spawnEmployees();
		spawnManagers();

		stopWatch.stop();
		System.out.format("Hunger started in %.2f s.", stopWatch.getTime() / 1000.0);
	}

	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		String input = "";

		while (active) {
			System.out.println("\nGame status: " + active);
			System.out.println("Type command: ");
			input = scan.nextLine();

			if (input.equalsIgnoreCase("stop")) {
				active = false;
				finishThreads();
				break;
			} else if (input.equalsIgnoreCase("stats")) {
				checkStats();
			} else {
				System.out.println("Unknown command");
			}
		}

		scan.close();
		System.out.println("CLI thread finished.");
	}

	// ------------------------ PRIVATE --------------------------------//

	private class StatsTimer extends TimerTask {

		@Override
		public void run() {
			Hunger.this.checkStats();
		}
	}

	private void startCliAndTimer() {
		System.out.println("Starting CLI thread and StatTimer");
		new Thread(this).start();
		statsTimer = new StatsTimer();
		timer = new Timer(false);
		timer.scheduleAtFixedRate(statsTimer, 1000, 5000);
		System.out.println("Done");
	}

	private void spawnManagers() {
		System.out.println("Spawning managers");
		for (int i = 0; i < 2; i++) {
			Manager mgr = new Manager("Manager #" + i, employees);
			new Thread(mgr).start();
			managers.add(mgr);
		}
		System.out.println("Done");

	}

	private void spawnEmployees() {
		System.out.println("Spawning employees");
		for (int i = 0; i < 5; i++) {
			employees.add(new Employee("Employee #" + i));
		}
		System.out.println("Done");
	}

	private void finishThreads() {
		try {
			timer.cancel();
			synchronized (statsTimer) {
				statsTimer.wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Manager mgr : managers) {
			mgr.setIsActive(false);
			try {
				synchronized (mgr) {
					mgr.wait(100);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.exit(0); 
	}

	private void checkStats() {
		boolean empsAlive = false;
		boolean mgrsAlive = false;

		System.out.println("\n\nStats:");
		for (Employee emp : employees) {
			System.out.println(emp.toString());
			if (emp.isAlive())
				empsAlive = true;
		}
		for (Manager mgr : managers) {
			System.out.println(mgr.toString());
			if (mgr.isAlive())
				mgrsAlive = true;
		}

		if (!empsAlive || !mgrsAlive) {
			active = false;
			if (mgrsAlive) {
				System.out.println("\nManagers win!");
			} else if (empsAlive) {
				System.out.println("\nEmployees win!");
			} else {
				System.out.println("\nEveryone's dead!");
			}
			finishThreads();
		}
	}
}
