/**
 * @copyright wxz
 */
package com.sgg.interview;

import org.junit.Test;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * 面试题1：三个线程顺序打印 A,B,C
 * 使用ReentrantLock
 */
public class ABCPrinter {

    @Test
    public void test() {
        new Thread(() -> print('A')).start();
        new Thread(() -> print('B')).start();
        new Thread(() -> print('C')).start();
    }

    public void print(char letter) {
        // 2024/4/17 没思路，能看懂
    }

}






















/*
// 方法1：
private final ReentrantLock lock = new ReentrantLock();
private final Condition condition = lock.newCondition();
private int count = 0;

public void print(char letter) {
    for (int i = 0; i < 100; i++) {
        lock.lock();
        try {
            while (count % 3 != (letter - 'A'))
                condition.await();  // 等待，直到条件符合

            out.print(letter);
            count++;  // 更新计数器
            condition.signalAll();  // 唤醒所有等待的线程
        } catch (InterruptedException e) {
            currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
*/