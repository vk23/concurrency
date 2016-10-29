package vk.nomercy.concurrency.matrices;

import static vk.nomercy.concurrency.Util.rnd;

/**
 * Created by vk on 28.10.2016
 */
public final class MatrixUtil {


    public static int[][] generateIntMatrix(int rowNum, int colNum, int min, int max) {
        int[][] result = new int[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                result[i][j] = rnd(min, max);
            }
        }

        return result;
    }


    /**
     * Transponses matrix by swapping rows and columns
     *
     * @param source original matrix
     * @return transponsed result
     */
    public static int[][] transponse(int[][] source) {
        int[][] result = new int[source[0].length][source.length];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                result[j][i] = source[i][j];
            }
        }
        return result;
    }


    /**
     * Prints 2-dimensional array to stdout
     *
     * @param msg    message
     * @param source array
     */
    public static void print(String msg, int[][] source) {
        System.out.println(msg);

        int n = source.length;
        int m = source[0].length;

        for (int i = 0; i < n; i++) {
            System.out.print("[");
            for (int j = 0; j < m; j++) {
                System.out.print("" + source[i][j]);
                if (j != m - 1) System.out.print(",");
            }
            System.out.println("]");
        }
    }
}
