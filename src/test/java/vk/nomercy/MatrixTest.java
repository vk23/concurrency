package vk.nomercy;

import org.junit.Test;
import vk.nomercy.concurrency.matrices.Matrix;
import vk.nomercy.concurrency.matrices.MatrixUtil;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by vk on 29.10.2016
 */
public class MatrixTest {

    @Test
    public void testMatrixTransponse() {
        int[][] source = {{1, 2}, {4, 5}, {7, 8}};
        int[][] expected = {{1, 4, 7}, {2, 5, 8}};
        int[][] transponsed = MatrixUtil.transponse(source);

        MatrixUtil.print("testMatrixTransponse", transponsed);

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
        MatrixUtil.print("Multiply result:", res.getArray());
        assertArrayEquals("Multiply Expected result={{3},{3}}", expected, res.getArray());

        Matrix res2 = matrix1.multiplyConcurrent(matrix2);
        MatrixUtil.print("Multiply2 (concurrent) result:", res2.getArray());
        assertArrayEquals("Multiply2 (concurrent) Expected result={{3},{3}}", expected, res2.getArray());
    }
}
