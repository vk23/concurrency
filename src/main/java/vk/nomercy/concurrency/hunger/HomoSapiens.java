package vk.nomercy.concurrency.hunger;

import vk.nomercy.concurrency.Util;

public abstract class HomoSapiens {

	protected final String name;
	protected int iq;

	public HomoSapiens() {
		this("Unknown-" + Util.rnd(0, 1000));
	}

	public HomoSapiens(String name) {
		this.name = name;
		init();
	}

	public boolean isAlive() {
		return iq > 0;
	}

	protected void evolve(boolean positive) {
		if (positive)
			iq += Const.BRAIN_UP;
		else
			iq -= Const.BRAIN_DMG;
		checkBrain();
	}

	protected abstract void init();

	protected abstract void checkBrain();
}
