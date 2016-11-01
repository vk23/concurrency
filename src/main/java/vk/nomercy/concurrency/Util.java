package vk.nomercy.concurrency;

import java.util.Random;

/**
 * Created by vk on 29.10.2016
 */
public class Util {

	private static Random random = new Random();

	/**
	 * Generates random int x: min <= x < max
	 *
	 * @param min
	 *            min value (inclusive)
	 * @param max
	 *            max value (exclusive)
	 * @return random int
	 */
	public static int rnd(int min, int max) {
		return rnd(random, min, max);
	}

	/**
	 * Generates random int x: min <= x < max
	 *
	 * @param random
	 *            thread's own Random object
	 *
	 * @param min
	 *            min value (inclusive)
	 * @param max
	 *            max value (exclusive)
	 * @return random int
	 */
	public static int rnd(Random random, int min, int max) {
		assert max > min;
		return random.nextInt(max - min) + min;
	}
}
