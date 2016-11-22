package vk.nomercy.concurrency;

import vk.nomercy.concurrency.hunger.Hunger;
import vk.nomercy.concurrency.matrices.MatrixMultiplication;
import vk.nomercy.concurrency.sleepybarber.SleepingBarber;

public class App {

	public static void main(String[] args) {

		String usage = "\nUsage:\n" + "java -jar concurrency.jar <command> <options>\n\n---\n"
				+ "java -jar concurrency.jar help:\nprint this.\n\n" + "java -jar concurrency.jar mm <NxMxX>:\n"
				+ "Matrix multiplication (simple), <NxMxX> - matrices dimensions,\n"
				+ "where N - number of rows for the first matrix,\n" + "M - number of columns for the second,\n"
				+ "X - common dimension (required for mutliplication): "
				+ "number of columns for the 1st and number of rows for the 2nd.\n"
				+ "Example: 100x1000x200 = 100x200 * 200x1000\n\n---\n" + "java -jar concurrency.jar mmc <NxMxX>:\n"
				+ "Matrix multiplication (simple), <NxMxX> - matrices dimensions.\n\n---\n"
				+ "java -jar concurrency.jar hg <numOfManagers> <numOfEmployees>:\n"
				+ "Hunger game. Example: java -jar concurrenyc.jar hg 2 5\n\n---\n"
				+ "java -jar concurrency.jar sb <numOfClients>:\n"
				+ "Sleeping barber simulation using BlockingQueue. Example: java -jar concurrenyc.jar sb 10\n\n---\n";

		if (args.length == 0) {
			System.out.println("No arguments given. Exiting.\n");
			System.out.println(usage);
			System.exit(0);
		}

		MatrixMultiplication matrixMultiplier;

		String command = args[0].toLowerCase();
		switch (command) {
		// matrix multiplication
		case "mm":
		case "mmc":
			if (args.length > 1) {
				String[] dim = args[1].toLowerCase().split("x");
				matrixMultiplier = new MatrixMultiplication(Integer.parseInt(dim[0]), Integer.parseInt(dim[1]),
						Integer.parseInt(dim[2]));
			} else {
				matrixMultiplier = new MatrixMultiplication();
			}

			if (command.equals("mmc"))
				matrixMultiplier.multiplyConcurrent();
			else
				matrixMultiplier.multiplySimple();

			break;
		// hunger
		case "hg":
			int numOfEmployees = 5, numOfManagers = 2;
			if (args.length > 1) {
				numOfManagers = Integer.parseInt(args[1]);
			}
			if (args.length > 2) {
				numOfEmployees = Integer.parseInt(args[2]);
			}
			Hunger hunger = new Hunger(numOfManagers, numOfEmployees);
			hunger.startGame();
			break;
		case "sb":
			int numOfClients = 10;
			if (args.length > 1) {
				numOfClients = Integer.parseInt(args[1]);
			}
			SleepingBarber sb = new SleepingBarber();
			sb.start(numOfClients);
			break;
		// help
		case "help":
			System.out.println(usage);
			break;
		// unknown
		default:
			throw new IllegalArgumentException("Unknown command: " + command);
		}
	}

}
