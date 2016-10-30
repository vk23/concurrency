package vk.nomercy.concurrency;

import vk.nomercy.concurrency.matrices.MatrixMultiplication;

public class App {

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("No arguments given. Exiting.");
			System.exit(0);
		}

		String action = args[0].toLowerCase();
		switch (action) {
		case "mm":
			MatrixMultiplication matrixMultiplier = new MatrixMultiplication();
			matrixMultiplier.multiplySimple();
			break;
		case "mmc":
			MatrixMultiplication matrixMultiplier2 = new MatrixMultiplication();
			matrixMultiplier2.multiplyConcurrent();
			break;
		default:
			throw new IllegalArgumentException("Unknown command: " + action);
		}
	}
}
