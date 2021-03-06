package vk.nomercy.concurrency.hunger;

import lombok.extern.slf4j.Slf4j;
import vk.nomercy.concurrency.Util;

import java.util.Random;

@Slf4j
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

    public synchronized boolean eatBrain(Manager predator) {
        if (!isAlive())
            return false;

        boolean success = fightBack(predator);
        evolve(success);
        return !success;
    }

    @Override
    protected void evolve(boolean value) {
        super.evolve(value);

        if (iq < Const.IQ_WHINING_LIMIT) {
            log.info("\n/*" + name + ": I'm dying. Ruthless managers ate my brain*/");
        }
    }

    private boolean fightBack(Manager predator) {
        Random random = predator.getRandom();
        int attack = Util.rnd(random, 0, predator.strength());
        int defense = Util.rnd(random, 0, strength());

        return attack <= defense;
    }

    @Override
    public String toString() {
        return name + ": " + (!isAlive() ? "dead" : iq);
    }

    @Override
    public int strength() {
        return iq;
    }
}
