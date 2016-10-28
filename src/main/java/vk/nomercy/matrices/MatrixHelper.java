package vk.nomercy.matrices;

import org.apache.commons.lang3.time.StopWatch;

/**
 * Created by vk on 28.10.2016
 */
public class MatrixHelper {

    private int n = 2000;
    private int m = 2000;
    private int x = 3000;
    private int min = -100;
    private int max = 100;
    private int[][] matrix1;
    private int[][] matrix2;

    public MatrixHelper() {
        this.matrix1 = Util.generateIntMatrix(n, x, min, max);
        this.matrix2 = Util.generateIntMatrix(x, m, min, max);
    }

    public void multiply() {
        System.out.println("Start multiplying (simple)");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // multiply
        Matrix m1 = new Matrix(this.matrix1);
        Matrix m2 = new Matrix(this.matrix2);
        Matrix res = m1.multiply(m2);

        stopWatch.stop();
        System.out.format("Multiplied in %d ms.%n", stopWatch.getTime());

//        Util.print("First matrix", matrix1.getArray());
//        Util.print("Second matrix", matrix2.getArray());
//        Util.print("Multiplication result", res.getArray());
    }

    public void multiplyConcurrent() {
        System.out.println("Start multiplying (concurrent)");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // multiply
        Matrix m1 = new Matrix(this.matrix1);
        Matrix m2 = new Matrix(this.matrix2);
        Matrix res = m1.multiplyConcurrent(m2);

        stopWatch.stop();
        System.out.format("Multiplied in %d ms.%n", stopWatch.getTime());

//        Util.print("First matrix", matrix1.getArray());
//        Util.print("Second matrix", matrix2.getArray());
//        Util.print("Multiplication result", res.getArray());
    }
}
