package vk.nomercy.concurrency.matrices;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

/**
 * Created by vk on 28.10.2016
 */
public class MatrixMultiplication {

	private int n = Matrix.DEFAULT_SIZE;
	private int m = Matrix.DEFAULT_SIZE;
	private int x = Matrix.DEFAULT_SIZE;
	private int min = Matrix.DEFAULT_MIN;
	private int max = Matrix.DEFAULT_MAX;

	private Matrix matrix1;
	private Matrix matrix2;

	public MatrixMultiplication() {
		this.matrix1 = new Matrix(MatrixUtil.generateIntMatrix(n, x, min, max));
		this.matrix2 = new Matrix(MatrixUtil.generateIntMatrix(x, m, min, max));
	}

	public MatrixMultiplication(int[][] m1, int[][] m2) {
		this.matrix1 = new Matrix(m1);
		this.matrix2 = new Matrix(m2);
	}

	// ---------------- just launchers -------------------//

	public Matrix multiplySimple() {
		System.out.println("Multiplication (simple) started.");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Matrix res = multiply(matrix1, matrix2);
		stopWatch.stop();

		System.out.format("Mutliplication (simple) finished in %d ms.%n", stopWatch.getTime());

		return res;
	}

	public Matrix multiplyConcurrent() {
		System.out.println("Start multiplying (concurrent)");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Matrix res = multiplyConcurrent(matrix1, matrix2);
		stopWatch.stop();

		System.out.format("Multiplied in %d ms.%n", stopWatch.getTime());

		return res;
	}

	// ---------------- real stuff -------------------//

	private Matrix multiply(Matrix one, Matrix two) {
		// TODO: check one.rows.length == two.columns.length
		int[][] oneSrc = one.getSource();
		int[][] twoSrc = two.getSource();
		int[][] result = new int[one.getRowNum()][two.getColNum()];

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		System.out.println("Transpose started");
		int[][] transposed = MatrixUtil.transpose(twoSrc);
		stopWatch.stop();
		System.out.format("Transpose finished in %d%n", stopWatch.getTime());

		for (int i = 0; i < oneSrc.length; i++) {
			int[] row = oneSrc[i];
			for (int j = 0; j < transposed.length; j++) {
				int[] column = transposed[j];
				result[i][j] = MatrixUtil.multiplyVectors(row, column);
			}
		}
		return new Matrix(result);
	}

	private Matrix multiplyConcurrent(Matrix one, Matrix two) {
		// TODO: check one.rows.length == two.columns.length
		int[][] oneSrc = one.getSource();
		int[][] twoSrc = two.getSource();
		int[][] result = new int[one.getRowNum()][two.getColNum()];

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		System.out.println("Transpose started");
		int[][] transposed = MatrixUtil.transpose(twoSrc);
		stopWatch.stop();
		System.out.format("Transpose finished in %d%n", stopWatch.getTime());

		int numOfProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(numOfProcessors);

		for (int i = 0; i < oneSrc.length; i++) {
			// multiply all rows from the second transposed matrix by the
			// current row
			executorService.execute(new Worker(result, i, oneSrc[i], transposed));
		}
		executorService.shutdown();

		try {
			executorService.awaitTermination(120, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Matrix(result);
	}

	public static class Worker implements Runnable {

		private int currentRowNum;
		private int[][] result;
		private int[][] m2;
		private int[] row;

		public Worker(int[][] result, int currentRowNum, int[] row, int[][] m2) {
			this.result = result;
			this.currentRowNum = currentRowNum;
			this.row = row;
			this.m2 = m2;
		}

		public void run() {
			try {
				for (int j = 0; j < m2.length; j++) {
					result[currentRowNum][j] = MatrixUtil.multiplyVectors(row, m2[j]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
