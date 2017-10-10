package vk.nomercy.concurrency.hunger;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.*;

@Slf4j
public class Hunger implements Runnable {

    private List<Employee> employees = new ArrayList<>();
    private List<Manager> managers = new ArrayList<>();
    private StatsTimer statsTimer;
    private Timer timer;
    private boolean active = true;
    private int numOfManagers;
    private int numOfEmployees;

    public Hunger(int numOfManagers, int numOfEmployees) {
        this.numOfManagers = numOfManagers;
        this.numOfEmployees = numOfEmployees;
    }

    public void startGame() {
        log.info("Hunger starting");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        spawnEmployees();
        spawnManagers();
        startCliAndTimer();

        stopWatch.stop();
        log.info("Hunger started in {} s.", stopWatch.getTime() / 1000.0);
    }

    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        String input = "";

        while (active) {
            log.info("\nGame status: " + active);
            log.info("Type command: ");
            input = scan.nextLine();

            if (input.equalsIgnoreCase("stop")) {
                active = false;
                finishThreads();
                break;
            } else if (input.equalsIgnoreCase("stats")) {
                checkStats();
            } else {
                log.info("Unknown command");
            }
        }

        scan.close();
        log.info("CLI thread finished.");
    }

    // ------------------------ PRIVATE --------------------------------//

    private class StatsTimer extends TimerTask {

        @Override
        public void run() {
            Hunger.this.checkStats();
        }
    }

    private void startCliAndTimer() {
        log.info("Starting CLI thread and StatTimer");
        new Thread(this).start();
        statsTimer = new StatsTimer();
        timer = new Timer(false);
        timer.scheduleAtFixedRate(statsTimer, 100, 5000);
        log.info("Done");
    }

    private void spawnManagers() {
        log.info("Spawning managers");
        for (int i = 0; i < numOfManagers; i++) {
            Manager mgr = new Manager("Manager #" + i, employees);
            new Thread(mgr).start();
            managers.add(mgr);
        }
        log.info("Done");

    }

    private void spawnEmployees() {
        log.info("Spawning employees");
        for (int i = 0; i < numOfEmployees; i++) {
            employees.add(new Employee("Employee #" + i));
        }
        log.info("Done");
    }

    private void finishThreads() {
        timer.cancel();

        for (Manager mgr : managers) {
            mgr.setIsActive(false);
            try {
                synchronized (mgr) {
                    mgr.wait(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.exit(0);
    }

    private void checkStats() {
        boolean empsAlive = false;
        boolean mgrsAlive = false;

        log.info("\n\nStats:");
        for (Employee emp : employees) {
            log.info(emp.toString());
            if (emp.isAlive())
                empsAlive = true;
        }
        for (Manager mgr : managers) {
            log.info(mgr.toString());
            if (mgr.isAlive())
                mgrsAlive = true;
        }

        if (!empsAlive || !mgrsAlive) {
            active = false;
            if (mgrsAlive) {
                log.info("\nManagers win!");
            } else if (empsAlive) {
                log.info("\nEmployees win!");
            } else {
                log.info("\nEveryone's dead!");
            }
            finishThreads();
        }
    }
}
