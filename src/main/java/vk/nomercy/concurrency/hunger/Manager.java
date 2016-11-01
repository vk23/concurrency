package vk.nomercy.concurrency.hunger;

import java.util.List;
import java.util.Random;

import vk.nomercy.concurrency.Util;

public class Manager extends HomoSapiens implements Runnable {

	private boolean active = true;
	private List<Employee> employees;
	private Random random = new Random();
	private int hungerValue;

	public Manager(String name, List<Employee> employees) {
		super(name);
		this.employees = employees;
	}

	public void setIsActive(boolean value) {
		this.active = value;
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
				int sleepTime = Util.rnd(random, Const.MANAGER_MIN_SLEEP_TIME, Const.MANAGER_MAX_SLEEP_TIME);
				Thread.sleep(sleepTime);
				prey();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void prey() {
		int index = Util.rnd(random, 0, employees.size());
		Employee employee = employees.get(index);
		boolean result = employee.eatBrain(this);
		evolve(result);
	}

	@Override
	protected void evolve(boolean positive) {
		if (positive) {
			hungerValue = Math.min(hungerValue + Const.HUNGER_DELTA, Const.HUNGER_MAX);
		} else {
			hungerValue = Math.max(hungerValue - Const.HUNGER_DELTA, Const.HUNGER_MIN);
		}
		super.evolve(positive);
	}

	@Override
	protected void checkBrain() {
		if (iq > Const.IQ) {
			iq = Const.IQ;
		} else if (iq <= 0) {
			iq = 0;
			active = false;
		} else if (iq < Const.IQ_WHINING_LIMIT) {
			System.out.println("\n/*" + name + ": I'm starving... I need your brain.*/");
		}
	}

	@Override
	public String toString() {
		return name + ": " + (!isAlive() ? "dead" : iq);
	}
}
