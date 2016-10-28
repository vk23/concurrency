package vk.nomercy.matrices;

import java.util.Random;

/**
 * Created by vk on 28.10.2016
 */
public final class Util {

    private static Random random = new Random();

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
     * Generates random int x: min <= x < max
     *
     * @param min min value (inclusive)
     * @param max max value (exclusive)
     * @return random int
     */
    public static int rnd(int min, int max) {
        assert max > min;
        return random.nextInt(max - min) + min;
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
