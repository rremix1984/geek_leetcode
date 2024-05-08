package com.leetcode.todo;

import org.junit.Test;
import java.util.Arrays;

/**
    [ARRAY]
    （简单）
    NO.3010 将数组分成最小总代价的子数组 I
    给你一个长度为 n 的整数数组 nums 。
    一个数组的 代价 是它的 第一个 元素。比方说，[1,2,3] 的代价是 1 ，[3,4,1] 的代价是 3 。
    你需要将 nums 分成 3 个 连续且没有交集 的子数组。
    请你返回这些子数组的 最小 代价 总和 。

    示例 1：
        输入：nums = [1,2,3,12]
        输出：6
        解释：最佳分割成 3 个子数组的方案是：[1] ，[2] 和 [3,12] ，总代价为 1 + 2 + 3 = 6 。
        其他得到 3 个子数组的方案是：
                - [1] ，[2,3] 和 [12] ，总代价是 1 + 2 + 12 = 15 。
                - [1,2] ，[3] 和 [12] ，总代价是 1 + 3 + 12 = 16 。
    示例 2：
        输入：nums = [5,4,3]
        输出：12
        解释：最佳分割成 3 个子数组的方案是：[5] ，[4] 和 [3] ，总代价为 5 + 4 + 3 = 12 。
                12 是所有分割方案里的最小总代价。
    示例 3：
        输入：nums = [10,3,1,1]
        输出：12
        解释：最佳分割成 3 个子数组的方案是：[10,3] ，[1] 和 [1] ，总代价为 10 + 1 + 1 = 12 。
                12 是所有分割方案里的最小总代价。
    提示：
        3 <= n <= 50
        1 <= nums[i] <= 50
    Related Topics:数组,枚举,排序
    解题方法:
        首先我们知道要分成三个数组，无论第一个数组怎么分都是第一个数据，所以我们先保留第一个，
    后面二个数组又因为要满足最小代价总和，所以我们直接找最小的二个数，这样一来是不是简单多了，
    我用的是在定义一个数组，把除了第一个数据以外的其他数据加到数组中，在进行从小到大排列，
    取前面二个，最后把数据相加就可以了
*/
@SuppressWarnings("all")
public class NO3010_E_MinimumCost {

    @Test
    public void test() {
        assert 6 == minimumCost(new int[]{1, 2, 3, 12});
        assert 12 == minimumCost(new int[]{5, 4, 3});
        assert 12 == minimumCost(new int[]{10, 3, 1, 1});
    }

    public int minimumCost(int[] nums) {
        return -1;
    }

}





















/*
// 方法1：
public int minimumCost(int[] nums) {
    int[] text = new int[nums.length - 1];
    for (int i = 1; i < nums.length; i++)
        text[i - 1] = nums[i];

    Arrays.sort(text);
    return nums[0] + text[0] + text[1];
}
*/