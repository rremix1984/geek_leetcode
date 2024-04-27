package com.interview.lang;

import static java.lang.System.out;

public class BoxingDemo {

    public static void main(String[] args) {
        out.println(
                "new Integer(2) == 2 ? "
                        + (new Integer(2) == 2));
        out.println(
                "new Integer(2) == new Integer(2) ? "
                        + (new Integer(2) == new Integer(2)));
        out.println(
                "Integer.valueOf(100) == Integer.valueOf(100) ? "
                        + (Integer.valueOf(100) == Integer.valueOf(100)));
        out.println(
                "Integer.valueOf(1000) == Integer.valueOf(1000) ? "
                        + (Integer.valueOf(1000) == Integer.valueOf(1000)));
        out.println(
                "Integer.valueOf(2).intValue() == 2 ? "
                        + (Integer.valueOf(2).intValue() == 2));
        out.println(
                "new Integer(2).equals(new Integer(2)) ? "
                        + (new Integer(2).equals(new Integer(2))));
    }
}
