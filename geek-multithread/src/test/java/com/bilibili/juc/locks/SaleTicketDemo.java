/**
 * @auther zzyy
 * @create 2022-01-18 10:15
 */
package com.bilibili.juc.locks;

import com.bilibili.juc.locks.util.Ticket;
import org.junit.Test;

/**
 *
 */
@SuppressWarnings("all")
public class SaleTicketDemo {

    @Test
    public void test() {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "a").start();

        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "b").start();

        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "c").start();
    }

}