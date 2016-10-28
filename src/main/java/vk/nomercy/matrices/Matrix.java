package vk.nomercy.matrices;

/**
 * Created by vk on 28.10.2016
 */
public class Matrix {

    private int[][] self;
    //    private int[][] other;
    private int rowNum;
    private int colNum;

    public Matrix(int[][] self) {
        this.self = self;
        this.rowNum = self.length;
        this.colNum = self[0].length;
    }

    public Matrix(int rowNum, int colNum) {
        this.self = Util.generateIntMatrix(rowNum, colNum, -100, 100);
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    public int[][] getArray() {
        return this.self;
    }

    public Matrix multiply(Matrix other) {
        int[][] otherSrc = other.getArray();
        if (colNum != otherSrc.length) {
            throw new IllegalArgumentException("Column number must be = " + rowNum);
        }

        int[][] result = new int[rowNum][otherSrc[0].length];
        int[][] transponsed = Util.transponse(otherSrc);

        for (int i = 0; i < rowNum; i++) {
            int[] row = this.self[i];
            for (int j = 0; j < transponsed.length; j++) {
                int[] column = transponsed[j];
                result[i][j] = multiplyVectors(row, column);
            }
        }
        return new Matrix(result);
    }

    private int multiplyVectors(int[] row, int[] column) {
        assert row.length == column.length;

        int result = 0;

        int size = row.length;
        for (int i = 0; i < size; i++) {
            result += row[i] * column[i];
        }

        return result;
    }

}
