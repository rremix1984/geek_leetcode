package com.leetcode;

import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.MathUtils.getDict;
import static com.leetcode.util.SystemUtil.*;

/**
    [ARRAY] ||||
    (中等,面试题)
    NO.442 数组中重复的数据
      给你一个长度为n的整数数组 nums，其中nums的所有整数都在范围 [1, n] 内，
    且每个整数出现一次或两次。请你找出所有出现两次的整数，并以数组形式返回。你
    必须设计并实现一个时间复杂度为 O(n) 且仅使用常量额外空间的算法解决此问题。
    示例 1：
        输入：nums = [4, 3, 2, 7, 8, 2, 3, 1]
        输出：[2, 3]
    示例 2：
        输入：nums = [1, 1, 2]
        输出：[1]
    示例 3：
        输入：nums = [1]
        输出：[]
    提示：
        n == nums.length
        1 <= n <= 105
        1 <= nums[i] <= n
        nums 中的每个元素出现 一次 或 两次
    Related Topics:数组,哈希表
*/
public class NO442_N_FindDuplicates {

    @Test
    public void test() {
        arrayAllMatch(getArray(2, 3),
                findDuplicates(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));
        arrayAllMatch(getArray(1),
                findDuplicates(new int[]{1, 1, 2}));
        arrayAllMatch(getArray(),
                findDuplicates(new int[]{1}));
    }

    public List<Integer> findDuplicates(int[] nums) {
        // 2024/3/16 NO.1 典型题，需要反复学
        // 2024/3/18 NO.2 不使用额外空间的方法没想出来
        // 2024/3/20 NO.3 还是不会
        // 2024/3/21 NO.4 还是不会
        List<Integer> ret = new ArrayList<>();
        return ret;
    }

}
















/*
// 方法1：
public List<Integer> findDuplicates(int[] nums) {
    List<Integer> ret = new ArrayList<>();
    int len = nums.length;
    for (int i : nums) {
        int j = (i - 1) % len;
        // 先把所有元素加上一个理论上最大的值 len
        nums[j] += len;
    }

    for (int i = 0; i < len; i++)
        // 因为前面加上了一个很大的值，
        // 只有重复出现2次以上才会满足下面条件
        if (nums[i] > 2 * len)
            ret.add(i + 1);

    return ret;
}
*/