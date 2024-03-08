/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.PriorityQueue;
import java.util.Queue;

/**
    [ARRAY] |
    (简单)
    1046. 最后一块石头的重量
        有一堆石头，每块石头的重量都是正整数。
        每一回合，从中选出两块【最重的】石头，然后将它们一起粉碎。
        假设石头的重量分别为x和y，且x <= y。那么粉碎的可能结果如下：
          1）如果x == y，那么两块石头都会被完全粉碎；
          2）如果x != y，那么重量为x的石头将会完全粉碎，而重量为y的石头新重量为y-x。
        最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
    示例：
        输入：{2, 7, 4, 1, 8, 1}
        输出：1
        解释：先选出 7 和 8，得到 1，所以数组转换为 {2, 4, 1, 1, 1}，
             再选出 2 和 4，得到 2，所以数组转换为 {2, 1, 1, 1}，
             接着是 2 和 1，得到 1，所以数组转换为 {1, 1, 1}，
            最后选出 1 和 1，得到 0，最终数组转换为 {1}，这就是最后剩下那块石头的重量。
*/
public class NO1046_E_LastStoneWeight {

    @Test
    public void test() {
        assert 1 == lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1});
    }

    public int lastStoneWeight(int[] stones) {
        // 2024/3/6 NO.1
        return -1;
    }

}

















/*
// 方法1：
public int lastStoneWeight(int[] stones) {
    Queue<Integer> queue =
            new PriorityQueue<>((a, b) -> b - a);

    for (int stone : stones)
        queue.offer(stone);

    while (queue.size() > 1) {
        int a = queue.poll();
        int b = queue.poll();
        if (a > b)
            queue.offer(a - b);
    }

    return queue.isEmpty() ? 0 : queue.poll();
}
*/