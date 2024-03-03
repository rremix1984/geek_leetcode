/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (中等)
    剑指 Offer II 060. 出现频率最高的 k 个数字
        给定一个整数数组 nums 和一个整数 k ，请返回其中出现频率前 k 高的元素。可以按 任意顺序 返回答案。
    示例 1:
        输入: nums = [1, 1, 1, 2, 2, 3], k = 2
        输出: [1, 2]
    示例 2:
        输入: nums = [1], k = 1
        输出: [1]
*/
public class OfferII_060_N_TopKFrequent {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 2}, topKFrequent(getArrays(1, 1, 1, 2, 2, 3) , 2));
        assertArrayEquals(new int[]{1}, topKFrequent(getArrays(1) , 1));
    }

    // 方法2：使用 List.sort() 方法
    public int[] topKFrequent(int[] nums, int k) {
        int[] res = new int[k];

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums)
            map.put(num, map.getOrDefault(num, 0) + 1);

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(
            (a, b) -> b.getValue() - a.getValue()
        );

        for (int i = 0; i < k ; i++)
            res[i] = list.get(i).getKey();

        return res;
    }

}
















/**
// 方法1：
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> occurrences = new HashMap<>();
    for (int num : nums)
        occurrences.put(num, occurrences.getOrDefault(num, 0) + 1);

    // int[] 的第一个元素代表数组的值，第二个元素代表了该值出现的次数
    PriorityQueue<int[]> queue = new PriorityQueue<>(
            Comparator.comparingInt(m -> m[1])
    );

    for (Map.Entry<Integer, Integer> entry : occurrences.entrySet()) {
        int num = entry.getKey();
        int count = entry.getValue();
        if (queue.size() == k)
            if (queue.peek()[1] < count) {
                queue.poll();
                queue.offer(new int[]{num, count});
            }
            else
                queue.offer(new int[]{num, count});
    }
    int[] ret = new int[k];
    for (int i = 0; i < k; i++)
        ret[i] = queue.poll()[0];

    return ret;
}

// 方法2：使用 List.sort() 方法
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : nums)
        map.put(num, map.getOrDefault(num, 0) + 1);

    List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
    list.sort((a, b) -> b.getValue() - a.getValue());

    int[] res = new int[k];
    for (int i = 0; i < k ; i++)
        res[i] = list.get(i).getKey();

    return res;
}
*/