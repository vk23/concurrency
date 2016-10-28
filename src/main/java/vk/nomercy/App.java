package vk.nomercy;

import org.apache.commons.lang3.time.StopWatch;
import vk.nomercy.matrices.Matrix;
import vk.nomercy.matrices.Util;

public class App {

    public static void main(String[] args) {

        int rows = 90, cols = 80, common = 100;
        int min = -100;
        int max = 100;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // generate matrices
        int[][] m1 = Util.generateIntMatrix(rows, common, min, max);
        int[][] m2 = Util.generateIntMatrix(common, cols, min, max);

        stopWatch.split();
        System.out.format("Generated in %f ms.%n", stopWatch.getSplitNanoTime()/1000000f);
        stopWatch.unsplit();

        // multiply
        Matrix matrix1 = new Matrix(m1);
        Matrix matrix2 = new Matrix(m2);
        Matrix res = matrix1.multiply(matrix2);

        stopWatch.stop();
        System.out.format("Multiplied in %f ms.%n", stopWatch.getNanoTime()/1000000f);

//        Util.print("First matrix", matrix1.getArray());
//        Util.print("Second matrix", matrix2.getArray());
//        Util.print("Multiplication result", res.getArray());

    }
}
