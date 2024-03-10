/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.*;

import static com.leetcode.util.SystemUtil.printArr;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |||
    (中等)
    347. 前K个高频元素
        给你一个整数数组nums和一个整数k，请你返回其中出现频率前k高的元素。
        你可以按任意顺序返回答案。
    示例 1:
        输入: nums = {1, 1, 1, 2, 2, 3}, k = 2
        输出: {1, 2}
    示例 2:
        输入: nums = {1}, k = 1
        输出: {1}
*/
@SuppressWarnings("all")
public class NO347_N_TopKFrequentElements {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 1},
            topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2));
        assertArrayEquals(new int[]{1},
            topKFrequent(new int[]{1}, 1));
    }

    public int[] topKFrequent(int[] nums, int k) {
        // 2024/3/4 NO.1
        // 2024/3/8 NO.2 用哈希表记录出现频率
        // 2024/3/10 NO.3
        int[] ans = new int[k];
        return ans;
    }

}



















/*
// 方法1：优先队列
public int[] topKFrequent(int[] nums, int k) {
    int[] ans = new int[k];
    Map<Integer, Integer> map = new HashMap<>();
    for (int n : nums)
        map.put(n, map.getOrDefault(n, 0)+1);

    Set<Integer> set = map.keySet();
    Queue<Integer> queue = new PriorityQueue<>(
        (v1, v2) -> map.get(v1) - map.get(v2));

    for (Integer v : set) {
        queue.add(v);
        // 大于k个元素后，后面进入，前面出去，
        // 保持这个优先队列只有k个元素
        if (queue.size() > k)
            queue.poll();
    }

    for (int i = 0; i < k; i++)
        ans[i] = queue.poll();

    return ans;
}
*/