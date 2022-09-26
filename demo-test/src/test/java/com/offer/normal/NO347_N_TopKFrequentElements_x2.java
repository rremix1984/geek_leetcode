/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
    (中等)
    347. 前 K 个高频元素
        给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
    示例 1:
        输入: nums = {1, 1, 1, 2, 2, 3},  k = 2
        输出: {1, 2}
    示例 2:
        输入: nums = {1},  k = 1
        输出: {1}
*/
public class NO347_N_TopKFrequentElements_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 1}, topKFrequent(new int[]{1, 1, 1, 2, 2, 3},  2));
        assertArrayEquals(new int[]{1}, topKFrequent(new int[]{1},  1));
    }

    public int[] topKFrequent(int[] nums, int k) {
        int[] ans = new int[k];
        return ans;
    }

}



















/**
// 方法1：优先队列
public int[] topKFrequent(int[] nums, int k) {
    int[] ans = new int[k];
    Map<Integer, Integer> map = new HashMap<>();
    for (int n : nums)
        map.put(n, map.getOrDefault(n, 0)+1);

    Set<Integer> set = map.keySet();
    PriorityQueue<Integer> queue = new PriorityQueue<>((v1, v2) -> map.get(v1) - map.get(v2));
    for (Integer v : set) {
        queue.add(v);
        if (queue.size() > k)
            queue.poll();
    }

    for (int i = 0; i < k; i++)
        ans[i] = queue.poll();

    return ans;
}
*/