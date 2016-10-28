package vk.nomercy;

import vk.nomercy.matrices.Matrix;
import vk.nomercy.matrices.Util;

public class App {

    public static void main(String[] args) {
        int[][] m1 = Util.generateIntMatrix(3, 2, -10, 10);
        int[][] m2 = Util.generateIntMatrix(2, 3, -10, 10);
        Matrix matrix1 = new Matrix(m1);
        Matrix matrix2 = new Matrix(m2);
        Matrix res = matrix1.multiply(matrix2);

        Util.print("First matrix", matrix1.getArray());
        Util.print("Second matrix", matrix2.getArray());
        Util.print("Multiplication result", res.getArray());
    }
}
