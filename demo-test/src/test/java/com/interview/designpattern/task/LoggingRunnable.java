package com.interview.designpattern.task;

import static java.lang.System.out;

public class LoggingRunnable implements Runnable {

    private final Runnable innerRunnable;

    public LoggingRunnable(Runnable innerRunnable) {
        this.innerRunnable = innerRunnable;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        out.println("Task started at "
                + startTime);

        innerRunnable.run();

        out.println("Task finished. Elapsed time: "
                + (System.currentTimeMillis() - startTime));
    }

}
