package com.interview.designpattern.task;

import static java.lang.System.out;

public class CodingTask implements Runnable {

    private final int employeeId;

    public CodingTask(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public void run() {
        out.println("Employee " + employeeId
                + " started writing code.");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        out.println("Employee " + employeeId
                + " finished writing code.");
    }
}
