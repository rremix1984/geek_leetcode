package com.interview.designpattern.company;

import static java.lang.System.out;

public class Engineer implements Role {

    @Override
    public void doWork() {
        out.println("Doing engineer work.");
    }

    @Override
    public String toString() {
        return "Engineer";
    }
}
