package vk.nomercy.concurrency.hunger;

import vk.nomercy.concurrency.Util;

public final class Employee extends HomoSapiens {

	public Employee() {
		super();
	}

	public Employee(String name) {
		super(name);
	}

	@Override
	protected void init() {
		int delta = Util.rnd(Const.IQ_DELTA * -1, Const.IQ_DELTA + 1);
		iq = Const.IQ + delta;
	}

	public synchronized boolean eatBrain(HomoSapiens predator) {
		if (!isAlive())
			return false;

		boolean success = fightBack(predator);
		evolve(success);
		return !success;
	}

	@Override
	protected void checkBrain() {
		if (iq > Const.IQ_TOP_LIMIT) {
			iq = Const.IQ_TOP_LIMIT;
		} else if (iq <= 0) {
			iq = 0;
		} else if (iq < Const.IQ_WHINING_LIMIT) {
			System.out.println("\n/*" + name + ": I'm dying. Ruthless managers ate my brain*/");
		}
	}

	private boolean fightBack(HomoSapiens predator) {
		// TODO: depends on IQs
		return Util.rnd(0, 101) >= 50;
	}

	@Override
	public String toString() {
		return name + ": " + (!isAlive() ? "dead" : iq);
	}
}
