package vk.nomercy.concurrency.matrices;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by vk on 28.10.2016
 */
public class Matrix {

    private int[][] self;
    private int rowNum;
    private int colNum;

    public Matrix(int[][] self) {
        this.self = self;
        this.rowNum = self.length;
        this.colNum = self[0].length;
    }

    public Matrix(int rowNum, int colNum) {
        this.self = MatrixUtil.generateIntMatrix(rowNum, colNum, -100, 100);
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

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("Transponse started");
        int[][] transponsed = MatrixUtil.transpose(otherSrc);
        stopWatch.stop();
        System.out.format("Transponse finished in %d%n", stopWatch.getTime());

        for (int i = 0; i < rowNum; i++) {
            int[] row = this.self[i];
            for (int j = 0; j < transponsed.length; j++) {
                int[] column = transponsed[j];
                result[i][j] = multiplyVectors(row, column);
            }
        }
        return new Matrix(result);
    }

    private static int multiplyVectors(int[] row, int[] column) {
        assert row.length == column.length;

        int result = 0;

        int size = row.length;
        for (int i = 0; i < size; i++) {
            result += row[i] * column[i];
        }

        return result;
    }

    public Matrix multiplyConcurrent(Matrix other) {
        int[][] otherSrc = other.getArray();
        if (colNum != otherSrc.length) {
            throw new IllegalArgumentException("Column number must be = " + rowNum);
        }

        int[][] result = new int[rowNum][otherSrc[0].length];

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("Transponse started");
        int[][] transponsed = MatrixUtil.transpose(otherSrc);
        stopWatch.stop();
        System.out.format("Transponse finished in %d%n", stopWatch.getTime());

        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for (int i = 0; i < rowNum; i++) {
            int[] row = this.self[i];
            executorService.execute(new VectorMultipier2(result, i, row, transponsed));
//            for (int j = 0; j < transponsed.length; j++) {
//                int[] column = transponsed[j];
//                executorService.execute(new VectorMultipier(result, i, j, row, column));
//            }
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Matrix(result);
    }


    public static class VectorMultipier implements Runnable {

        private int i, j;
        private int[] row, column;
        private int[][] matrix;

        public VectorMultipier(int[][] matrix, int i, int j, int[] row, int[] column) {
            this.matrix = matrix;
            this.i = i;
            this.j = j;
            this.row = row;
            this.column = column;
        }

        public void run() {
            try {
                matrix[i][j] = multiplyVectors(row, column);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public static class VectorMultipier2 implements Runnable {

        private int i;
        private int[] row;
        private int[][] matrix;
        private int[][] m2;

        public VectorMultipier2(int[][] matrix, int i, int[] row, int[][] m2) {
            this.matrix = matrix;
            this.i = i;
//            this.j = j;
            this.row = row;
            this.m2 = m2;
//            this.column = column;
        }

        public void run() {
            try {
                for (int j = 0; j < m2.length; j++) {
                    matrix[i][j] = multiplyVectors(row, m2[j]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
}
