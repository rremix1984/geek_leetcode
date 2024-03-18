package com.interval;

import org.junit.Test;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (中等)
    Interval.17.14 最小K个数
    设计一个算法，找出数组中最小的k个数。以任意顺序返回这k个数均可。
    示例：
        输入： arr = [1, 3, 5, 7, 2, 4, 6, 8], k = 4
        输出： [1, 2, 3, 4]
    提示：
        0 <= len(arr) <= 100000
        0 <= k <= min(100000, len(arr))
    Related Topics:数组,分治,快速选择,排序,堆（优先队列）
*/
public class Interval_17_14_SmallestK {

    @Test
    public void test() {
        assertArrayEquals(getArrays(1, 2, 3, 4),
                smallestK(getArrays(1, 3, 5, 7, 2, 4, 6, 8), 4));
    }

    public int[] smallestK(int[] arr, int k) {
        int[] res = new int[k];
        Queue<Integer> heap = new PriorityQueue<>(k + 1);
        Arrays.stream(arr).forEach(
            num -> heap.offer(num)
        );

        int idx = 0;
        while (idx < k)
            res[idx++] = heap.poll();

        return res;
    }

}















/*
// 方法1：
public int[] smallestK(int[] arr, int k) {
    return Arrays.stream(arr).sorted().limit(k).toArray();
}

// 方法2：
public int[] smallestK(int[] arr, int k) {
    int[] res = new int[k];
    Queue<Integer> heap = new PriorityQueue<>(k + 1);
    Arrays.stream(arr).forEach(
            num -> heap.offer(num)
    );

    int idx = 0;
    while (idx < k)
        res[idx++] = heap.poll();

    return res;
}
*/

