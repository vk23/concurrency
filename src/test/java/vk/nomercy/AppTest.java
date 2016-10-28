package vk.nomercy;

import org.junit.Test;
import vk.nomercy.matrices.Util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

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

    @Test
    public void testMatrixTransponse() {
        int[][] source = {{1, 2}, {4, 5}, {7, 8}};
        int[][] expected = {{1, 4, 7}, {2, 5, 8}};
        int[][] transponsed = Util.transponse(source);

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                System.out.print(transponsed[i][j] + ",");
                assertEquals("a[" + i + "][" + j + "]=b[" + i + "][" + j + "]", expected[i][j], transponsed[i][j]);
            }
            System.out.println();
        }
    }


}
