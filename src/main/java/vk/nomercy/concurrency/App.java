package vk.nomercy.concurrency;

import vk.nomercy.concurrency.matrices.MatrixMultiplier;

public class App {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No arguments given. Exiting.");
            System.exit(0);
        }

        String action = args[0].toLowerCase();
        switch (action) {
            case "mm":
                MatrixMultiplier matrixMultiplier = new MatrixMultiplier();
                matrixMultiplier.multiply();
                break;
            case "mmc":
                MatrixMultiplier matrixMultiplier2 = new MatrixMultiplier();
                matrixMultiplier2.multiplyConcurrent();
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + action);
        }
    }
}
