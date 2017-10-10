package vk.nomercy.concurrency;


import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTest {

    private static final int NUM = 10;

    @RepeatedTest(NUM)
    public void testRandomGeneration() {
        int min = -5, max = 10;

        int generated = Util.rnd(min, max);

        boolean between = min <= generated && generated < max;
        assertTrue(between, String.format("Generated value = %d must be in [%d, %d)%n", generated, min, max));
    }

    @RepeatedTest(NUM)
    public void testRandomGenerationLocal() {
        int min = -5, max = 10;
        final Random random = new Random();

        int generated = Util.rnd(random, min, max);

        boolean between = min <= generated && generated < max;
        assertTrue(between, String.format("Generated value = %d must be in [%d, %d)%n", generated, min, max));
    }
}
