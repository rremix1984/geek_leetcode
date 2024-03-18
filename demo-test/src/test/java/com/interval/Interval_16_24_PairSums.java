package com.interval;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.SystemUtil.arrayAllMatch;
import static java.util.Arrays.asList;
import static java.util.Arrays.sort;

/**
    [ARRAY]
    (中等)
    Interval.16.24 数对和
    设计一个算法，找出数组中两数之和为指定值的所有整数对。一个数只能属于一个数对。
    示例 1:
        输入: nums = [5, 6, 5], target = 11
        输出: [[5, 6]]
    示例 2:
        输入: nums = [5, 6, 5, 6], target = 11
        输出: [[5, 6], [5, 6]]
    提示：
        nums.length <= 100000
        -10^5 <= nums[i], target <= 10^5
    Related Topics:数组,哈希表,双指针,计数,排序
*/
public class Interval_16_24_PairSums {

    @Test
    public void test() {
        arrayAllMatch(getArray(new int[][]{{5, 6}, {6, 5}}),
                pairSums(new int[]{5, 6, 5}, 11));
    }

    public List<List<Integer>> pairSums(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();

        //排序
        sort(nums);

        //头尾指针
        int i = 0;
        int j = nums.length - 1;

        while (i < j) {
            int sum = nums[i] + nums[j];
            //刚好相等。两个指针都往中间移动
            if (target == sum) {
                list.add(asList(nums[i], nums[j]));
                i++;
                j--;
            //两数之和太小，左指针右移，让和变大
            } else if (target > sum) {
                i++;
            //两数之和太大，右指针左移，让和变小
            } else {
                j--;
            }
        }
        return list;
    }

}
