package vk.nomercy.concurrency.hunger;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import vk.nomercy.concurrency.Util;

public class Manager extends HomoSapiens implements Runnable {

	private boolean active = true;
	private final List<Employee> employees;
	private final Random random = new Random();
	private int hungerValue;

	public Manager(String name, List<Employee> employees) {
		super(name);
		this.employees = employees;
	}

	public void setIsActive(boolean value) {
		this.active = value;
	}

	public Random getRandom() {
		return random;
	}

	@Override
	protected void init() {
		iq = Const.IQ;
		hungerValue = Const.HUNGER_MIN;
	}

	@Override
	public void run() {
		while (active) {
			try {
				// first sleep
				int sleepTime = Util.rnd(random, Const.MANAGER_MIN_SLEEP_TIME, Const.MANAGER_MAX_SLEEP_TIME);
				Thread.sleep(sleepTime);
				// then hunt
				prey();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void prey() {
		Employee employee = findAliveVictim();
		if (employee == null) {
			// System.out.println("No available victim found");
			return;
		}

		boolean result = employee.eatBrain(this);
		evolve(result);
	}

	private Employee findAliveVictim() {
		List<Employee> aliveEmployees = employees.stream().filter(e -> e.isAlive()).collect(Collectors.toList());
		if (aliveEmployees.isEmpty()) return null;
		
		// get random alive employee
		int index = Util.rnd(random, 0, aliveEmployees.size());
		Employee employee = aliveEmployees.get(index);
		return employee;
	}

	@Override
	protected void evolve(boolean positive) {

		if (!positive) {
			iq = Math.max(iq - Const.BRAIN_DMG, 0);
			hungerValue = Math.min(hungerValue + Const.HUNGER_DELTA, Const.HUNGER_MAX);
		} else {
			// managers IQ cannot be more than ordinary
			iq = Math.min(iq + Const.BRAIN_HEAL, Const.IQ);
			hungerValue = Math.max(hungerValue - Const.HUNGER_DELTA, Const.HUNGER_MIN);
		}

		if (iq <= 0) {
			active = false; // deactivating dead manager
		} else if (iq < Const.IQ_WHINING_LIMIT) {
			System.out.println("\n/*" + name + ": I'm starving... I need your brain.*/");
		}
	}

	@Override
	public String toString() {
		return name + ": " + (!isAlive() ? "dead" : iq) + ", hunger: " + hungerValue;
	}

	@Override
	public int getStrength() {
		return iq + hungerValue;
	}
}
