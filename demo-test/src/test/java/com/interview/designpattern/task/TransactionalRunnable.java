package com.interview.designpattern.task;

import static java.lang.System.out;

public class TransactionalRunnable implements Runnable {

    private final Runnable innerRunnable;

    public TransactionalRunnable(Runnable innerRunnable) {
        this.innerRunnable = innerRunnable;
    }

    @Override
    public void run() {
        boolean shouldRollback = false;
        try {
            beginTransaction();
            innerRunnable.run();
        } catch (Exception e) {
            shouldRollback = true;
            throw e;
        } finally {
            if (shouldRollback) {
                rollback();
            } else {
                commit();
            }
        }
    }

    private void commit() {
        out.println("commit");
    }

    private void rollback() {
        out.println("rollback");
    }

    private void beginTransaction() {
        out.println("beginTransaction");
    }
}
