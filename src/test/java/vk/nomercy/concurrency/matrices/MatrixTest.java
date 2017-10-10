package vk.nomercy.concurrency.matrices;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by vk on 29.10.2016
 */
public class MatrixTest {

    @Test
    public void testMatrixTranspose() {
        int[][] source = {{1, 2}, {4, 5}, {7, 8}};
        int[][] expected = {{1, 4, 7}, {2, 5, 8}};
        int[][] transposed = MatrixUtil.transpose(source);

        MatrixUtil.print("testMatrixTranspose", transposed);

        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], transposed[i]);
        }
    }

    @Test
    public void shouldCreateDefaultMatrix() {
        final int rows = 10;
        final int cols = 15;

        Matrix matrix = new Matrix(rows, cols);

        assertNotNull(matrix);
        assertNotNull(matrix.getSource());
        assertEquals(matrix.getRowNum(), rows);
        assertEquals(matrix.getColNum(), cols);
    }

    @Test
    public void testMatrixMultiplication() {
        int[][] m1 = {{1, 2}, {4, 5}, {7, 8}};
        int[][] m2 = {{1, 4, 7}, {2, 5, 8}};
        int[][] expected = {{5, 14, 23}, {14, 41, 68}, {23, 68, 113}};

        Matrix res = new MatrixMultiplication(m1, m2).multiplySimple();
        MatrixUtil.print("Multiply result:", res.getSource());
        assertArrayEquals(expected, res.getSource());

        Matrix res2 = new MatrixMultiplication(m1, m2).multiplyConcurrent();
        MatrixUtil.print("Multiply2 (concurrent) result:", res2.getSource());
        assertArrayEquals(expected, res2.getSource());
    }

    @Test
    public void testDefaultMatrixMultiplication() {
        MatrixMultiplication matrixMultiplication = new MatrixMultiplication();
        Matrix simpleProduct = matrixMultiplication.multiplySimple();
        Matrix concurrentProduct = matrixMultiplication.multiplyConcurrent();

        assertNotNull(simpleProduct);
        assertNotNull(concurrentProduct);
        assertEquals(simpleProduct.getColNum(), concurrentProduct.getColNum());
        for (int i = 0; i < simpleProduct.getRowNum(); i++) {
            assertArrayEquals(simpleProduct.getSource()[i], concurrentProduct.getSource()[i]);
        }
    }

    @Test
    public void testDefaultMatrixMultiplication2() {
        final int n = 100;
        final int m = 150;
        final int x = 200;
        MatrixMultiplication matrixMultiplication = new MatrixMultiplication(n, m, x);
        Matrix simpleProduct = matrixMultiplication.multiplySimple();
        Matrix concurrentProduct = matrixMultiplication.multiplyConcurrent();

        assertEquals(simpleProduct.getColNum(), m);
        assertEquals(simpleProduct.getRowNum(), n);
        assertNotNull(simpleProduct);
        assertNotNull(concurrentProduct);
        assertEquals(simpleProduct.getColNum(), concurrentProduct.getColNum());
        for (int i = 0; i < simpleProduct.getRowNum(); i++) {
            assertArrayEquals(simpleProduct.getSource()[i], concurrentProduct.getSource()[i]);
        }
    }

    @Test
    public void testMatricesCanNotBeMultiplied() {
        int[][] arr1 = {{1, 2}, {3, 4}};
        int[][] arr2 = {{1}, {2}, {3}};
        Matrix m1 = new Matrix(arr1);
        Matrix m2 = new Matrix(arr2);

        assertFalse(MatrixUtil.canBeMultiplied(arr1, arr2));
        assertFalse(MatrixUtil.canBeMultiplied(m1, m2));
    }
}
