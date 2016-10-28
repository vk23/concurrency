package vk.nomercy;

import vk.nomercy.matrices.MatrixHelper;

public class App {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No arguments given. Exiting.");
            System.exit(0);
        }

        String action = args[0].toLowerCase();
        switch (action) {
            case "mm":
                MatrixHelper matrixHelper = new MatrixHelper();
                matrixHelper.multiply();
                break;
            case "mmc":
                MatrixHelper matrixHelper2 = new MatrixHelper();
                matrixHelper2.multiplyConcurrent();
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + action);
        }
    }
}
