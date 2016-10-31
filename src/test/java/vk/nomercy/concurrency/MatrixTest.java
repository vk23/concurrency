package vk.nomercy.concurrency;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import vk.nomercy.concurrency.matrices.Matrix;
import vk.nomercy.concurrency.matrices.MatrixMultiplication;
import vk.nomercy.concurrency.matrices.MatrixUtil;

/**
 * Created by vk on 29.10.2016
 */
public class MatrixTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testMatrixTranspose() {
		int[][] source = { { 1, 2 }, { 4, 5 }, { 7, 8 } };
		int[][] expected = { { 1, 4, 7 }, { 2, 5, 8 } };
		int[][] transposed = MatrixUtil.transpose(source);

		MatrixUtil.print("testMatrixTranspose", transposed);

		for (int i = 0; i < expected.length; i++) {
			for (int j = 0; j < expected[0].length; j++) {
				assertEquals("a[" + i + "][" + j + "]=b[" + i + "][" + j + "]", expected[i][j], transposed[i][j]);
			}
		}
	}

	@Test
	public void testMatrixMultiplication() {
		int[][] m1 = { { 1, 2 }, { 4, 5 }, { 7, 8 } };
		int[][] m2 = { { 1, 4, 7 }, { 2, 5, 8 } };
		int[][] expected = { { 5, 14, 23 }, { 14, 41, 68 }, { 23, 68, 113 } };

		Matrix res = new MatrixMultiplication(m1, m2).multiplySimple();
		MatrixUtil.print("Multiply result:", res.getSource());
		assertArrayEquals("Multiply result", expected, res.getSource());

		Matrix res2 = new MatrixMultiplication(m1, m2).multiplyConcurrent();
		MatrixUtil.print("Multiply2 (concurrent) result:", res2.getSource());
		assertArrayEquals("Multiply2 (concurrent) result", expected, res2.getSource());
	}

	@Test
	public void testMatricesCanNotBeMultiplied() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Cannot multiply these matrices");

		int[][] m1 = { { 1, 2 }, { 3, 4 } };
		int[][] m2 = { { 1 }, { 2 }, { 3 } };

		if (!MatrixUtil.canBeMultiplied(m1, m2)) {
			throw new IllegalArgumentException("Cannot multiply these matrices");
		}
	}
}
