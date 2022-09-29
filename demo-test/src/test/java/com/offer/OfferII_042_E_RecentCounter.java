/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
    (简单)
    剑指 Offer II 042. 最近请求次数
        写一个 RecentCounter 类来计算特定时间范围内最近的请求。
        请实现 RecentCounter 类：
            RecentCounter() 初始化计数器，请求数为 0 。
            int ping(int t) 在时间 t 添加一个新请求，其中 t 表示以毫秒为单位的某个时间，
            并返回过去 3000 毫秒内发生的所有请求数（包括新请求）。确切地说，返回在 [t-3000, t] 内发生的请求数。
            保证 每次对 ping 的调用都使用比之前更大的 t 值。
    示例：
        输入：
            inputs = ["RecentCounter", "ping", "ping", "ping", "ping"]
            inputs = [[], [1], [100], [3001], [3002]]
        输出：
            [null, 1, 2, 3, 3]

        解释：
            RecentCounter recentCounter = new RecentCounter();
            recentCounter.ping(1);     // requests = [1]，范围是 [-2999,1]，返回 1
            recentCounter.ping(100);   // requests = [1, 100]，范围是 [-2900,100]，返回 2
            recentCounter.ping(3001);  // requests = [1, 100, 3001]，范围是 [1,3001]，返回 3
            recentCounter.ping(3002);  // requests = [1, 100, 3001, 3002]，范围是 [2,3002]，返回 3
*/
public class OfferII_042_E_RecentCounter {

    @Test
    public void test() {
        RecentCounter recentCounter = new RecentCounter();
        assert 1 == recentCounter.ping(1);   // requests = [1]，范围是 [-2999,1]，返回 1
        assert 2 == recentCounter.ping(100); // requests = [1, 100]，范围是 [-2900,100]，返回 2
        assert 3 == recentCounter.ping(3001);// requests = [1, 100, 3001]，范围是 [1,3001]，返回 3
        assert 3 == recentCounter.ping(3002);// requests = [1, 100, 3001, 3002]，范围是 [2,3002]，返回 3
    }

    static class RecentCounter {

        public RecentCounter() {

        }

        public int ping(int t) {
            return -1;
        }
    }


}



















/**
// 方法1：
static class RecentCounter {

    Queue<Integer> queue;

    public RecentCounter() {
        queue = new LinkedList<Integer>();
    }

    public int ping(int t) {
        queue.offer(t);
        while (queue.peek() < t - 3000) {
            queue.poll();
        }
        return queue.size();
    }
}
*/