package vk.nomercy;

import org.junit.Test;
import vk.nomercy.matrices.Matrix;
import vk.nomercy.matrices.Util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

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

        Util.print("testMatrixTransponse", transponsed);

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                assertEquals("a[" + i + "][" + j + "]=b[" + i + "][" + j + "]", expected[i][j], transponsed[i][j]);
            }
        }
    }

    @Test
    public void testMatrixMultiplication() {
        int[][] m1 = new int[][]{{1, 2}, {1, 2}};
        int[][] m2 = new int[][]{{1}, {1}};
        int[][] expected = new int[][]{{3}, {3}};

        Matrix matrix1 = new Matrix(m1);
        Matrix matrix2 = new Matrix(m2);
        Matrix res = matrix1.multiply(matrix2);

        Util.print("testMatrixMultiplication", res.getArray());

        assertArrayEquals("Expected result={{3},{3}}", expected, res.getArray());
    }

}
