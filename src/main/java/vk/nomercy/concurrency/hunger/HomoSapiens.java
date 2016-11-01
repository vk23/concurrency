package vk.nomercy.concurrency.hunger;

import vk.nomercy.concurrency.Util;

public abstract class HomoSapiens implements Strong {

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
		if (positive) {
			iq = Math.min(iq + Const.BRAIN_HEAL, Const.IQ_TOP_LIMIT);
		} else {
			iq = Math.max(iq - Const.BRAIN_DMG, 0);
		}
	}

	protected abstract void init();

}
