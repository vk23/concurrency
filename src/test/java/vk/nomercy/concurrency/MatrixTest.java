package vk.nomercy.concurrency;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import vk.nomercy.concurrency.matrices.Matrix;
import vk.nomercy.concurrency.matrices.MatrixUtil;

/**
 * Created by vk on 29.10.2016
 */
public class MatrixTest {

	@Test
	public void testMatrixTransponse() {
		int[][] source = { { 1, 2 }, { 4, 5 }, { 7, 8 } };
		int[][] expected = { { 1, 4, 7 }, { 2, 5, 8 } };
		int[][] transponsed = MatrixUtil.transpose(source);

		MatrixUtil.print("testMatrixTransponse", transponsed);

		for (int i = 0; i < expected.length; i++) {
			for (int j = 0; j < expected[0].length; j++) {
				assertEquals("a[" + i + "][" + j + "]=b[" + i + "][" + j + "]", expected[i][j], transponsed[i][j]);
			}
		}
	}

	@Test
	public void testMatrixMultiplication() {
		int[][] m1 = { { 1, 2 }, { 4, 5 }, { 7, 8 } };
		int[][] m2 = { { 1, 4, 7 }, { 2, 5, 8 } };
		int[][] expected = { { 5, 14, 23 }, { 14, 41, 68 }, { 23, 68, 113 } };

		Matrix matrix1 = new Matrix(m1);
		Matrix matrix2 = new Matrix(m2);

		Matrix res = matrix1.multiply(matrix2);
		MatrixUtil.print("Multiply result:", res.getArray());
		assertArrayEquals("Multiply result", expected, res.getArray());

		Matrix res2 = matrix1.multiplyConcurrent(matrix2);
		MatrixUtil.print("Multiply2 (concurrent) result:", res2.getArray());
		assertArrayEquals("Multiply2 (concurrent) result", expected, res2.getArray());
	}
}
