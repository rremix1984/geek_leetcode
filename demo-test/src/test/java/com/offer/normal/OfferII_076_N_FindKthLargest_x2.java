/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;
import static com.leetcode.util.SwapUtil.swap;

/**
    (中等)
    剑指 Offer II 076. 数组中的第 k 大的数字
        给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
        请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
    示例 1:
        输入: [3, 2, 1, 5, 6, 4] 和 k = 2
        输出: 5
    示例 2:
        输入: [3, 2, 3, 1, 2, 4, 5, 5, 6] 和 k = 4
        输出: 4
*/
public class OfferII_076_N_FindKthLargest_x2 {

    @Test
    public void test() {
        assert 5 == findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2);
        assert 4 == findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6, 2, 1}, 4);
    }

    public int findKthLargest(int[] nums, int k) {
        return -1;
    }

}



















/**
// 方法1：优先队列
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    for (int num : nums) {
        queue.offer(num);
        if (queue.size() > k)
            //如果堆存放数量超过k，移除堆头最小元素
            queue.poll();
    }
    return queue.poll();
}


// 方法2：
public int findKthLargest(int[] nums, int k) {
    Arrays.sort(nums);
    return nums[nums.length-k];
}


// 方法3：冒泡排序
public int findKthLargest(int[] nums, int k) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = 1; j < nums.length - i; j++)
            if (nums[j] < nums[j - 1])
                swap(nums, j, j - 1);

        if (i + 1 == k)
            return nums[nums.length - k];
    }
    return -1;
}
*/