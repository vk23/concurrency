package vk.nomercy.concurrency;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import vk.nomercy.concurrency.Util;

public class AppTest {

	@Test
	public void testRandom() {
		int min = -5, max = 10, size = 100;

		for (int i = 0; i < size; i++) {
			int generated = Util.rnd(min, max);
			assertTrue("Generated value must be between " + min + " and " + max + ", value = " + generated,
					min <= generated && generated < max);
		}
	}

}
