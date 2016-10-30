package vk.nomercy.concurrency.matrices;

/**
 * Created by vk on 28.10.2016
 */
public class Matrix {

	public static final int DEFAULT_SIZE = 2000;
	public static final int DEFAULT_MIN = -100;
	public static final int DEFAULT_MAX = 100;

	private final int[][] self;
	private final int rowNum;
	private final int colNum;

	public Matrix(int[][] self) {
		this.self = self;
		this.rowNum = self.length;
		this.colNum = self[0].length;
	}

	public Matrix(int rowNum, int colNum) {
		this.self = MatrixUtil.generateIntMatrix(rowNum, colNum, DEFAULT_MIN, DEFAULT_MAX);
		this.rowNum = rowNum;
		this.colNum = colNum;
	}

	public int[][] getSource() {
		return this.self;
	}

	public int getRowNum() {
		return this.rowNum;
	}

	public int getColNum() {
		return this.colNum;
	}
}
