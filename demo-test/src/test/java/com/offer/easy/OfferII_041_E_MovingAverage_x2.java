/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import java.util.LinkedList;
import java.util.Queue;

/**
    (简单)
    剑指 Offer II 041. 滑动窗口的平均值
        给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算滑动窗口里所有数字的平均值。
        实现 MovingAverage 类：
            MovingAverage(int size) 用窗口大小 size 初始化对象。
            double next(int val) 成员函数 next 每次调用的时候都会往滑动窗口增加一个整数，
            请计算并返回数据流中最后 size 个值的移动平均值，即滑动窗口里所有数字的平均值。
    示例：
        输入：
            inputs = {"MovingAverage", "next", "next", "next", "next"}
            inputs = {{3}, {1}, {10}, {3}, {5}}
        输出：
            {null, 1.0, 5.5, 4.66667, 6.0}
        解释：
            MovingAverage movingAverage = new MovingAverage(3);
            movingAverage.next(1); // 返回 1.0 = 1 / 1
            movingAverage.next(10);// 返回 5.5 = (1 + 10) / 2
            movingAverage.next(3); // 返回 4.66667 = (1 + 10 + 3) / 3
            movingAverage.next(5); // 返回 6.0 = (10 + 3 + 5) / 3
*/
public class OfferII_041_E_MovingAverage_x2 {

    @Test
    public void test() {
        MovingAverage movingAverage = new MovingAverage(3);
        assert 1.0 == movingAverage.next(1); // 返回 1.0 = 1 / 1
        assert 5.5 == movingAverage.next(10); // 返回 5.5 = (1 + 10) / 2
        assert 4.666666666666667 == movingAverage.next(3); // 返回 4.66667 = (1 + 10 + 3) / 3
        assert 6.0 == movingAverage.next(5); // 返回 6.0 = (10 + 3 + 5) / 3
    }

    static class MovingAverage {

        public MovingAverage(int size) {
            // TODO
        }

        public double next(int val) {
            return 0.0;
        }

    }

}


















/**
// 方法1：利用队列特性：后面进（offer），前面出（poll）
class MovingAverage {

    Queue<Integer> queue;
    int size;
    double sum;

    public MovingAverage(int size) {
        queue = new LinkedList<>();
        this.size = size;
        sum = 0;
    }

    public double next(int val) {
        if (queue.size() == size)
            sum -= queue.poll();

        queue.offer(val);
        sum += val;
        return sum / queue.size();
    }
}
*/