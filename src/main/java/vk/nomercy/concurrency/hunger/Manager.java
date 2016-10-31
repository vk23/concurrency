package vk.nomercy.concurrency.hunger;

import java.util.List;

import vk.nomercy.concurrency.Util;

public class Manager extends HomoSapiens implements Runnable {

	private boolean active = true;
	private List<Employee> employees;

	public Manager(String name, List<Employee> employees) {
		super(name);
		this.employees = employees;
	}

	public void setIsActive(boolean value) {
		this.active = value;
	}

	@Override
	protected void init() {
		iq = Const.IQ - Const.BRAIN_DMG;
	}

	@Override
	public void run() {
		while (active) {
			try {
				prey();
				Thread.sleep(Const.MANAGER_SLEEP_MS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void prey() {
		int index = Util.rnd(0, employees.size());
		Employee employee = employees.get(index);
		boolean result = employee.eatBrain(this);
		evolve(result);
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
