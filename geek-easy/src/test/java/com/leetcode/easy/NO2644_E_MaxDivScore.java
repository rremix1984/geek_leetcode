package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;

/**
    [ARRAY]
    （简单）
    NO.2644 找出可整除性得分最大的整数
    给你两个下标从 0 开始的整数数组 nums 和 divisors 。
    divisors[i] 的 可整除性得分 等于满足 nums[j] 能被 divisors[i] 整除的下标 j 的数量。
    返回 可整除性得分 最大的整数 divisors[i] 。如果有多个整数具有最大得分，则返回数值最小的一个。
    示例 1：
        输入：nums = [4,7,9,3,9], divisors = [5,2,3]
        输出：3
        解释：divisors 中每个元素的可整除性得分为：
        divisors[0] 的可整除性得分为 0 ，因为 nums 中没有任何数字能被 5 整除。
        divisors[1] 的可整除性得分为 1 ，因为 nums[0] 能被 2 整除。
        divisors[2] 的可整除性得分为 3 ，因为 nums[2]、nums[3] 和 nums[4] 都能被 3 整除。
        因此，返回 divisors[2] ，它的可整除性得分最大。
    示例 2：
        输入：nums = [20,14,21,10], divisors = [5,7,5]
        输出：5
        解释：divisors 中每个元素的可整除性得分为：
        divisors[0] 的可整除性得分为 2 ，因为 nums[0] 和 nums[3] 都能被 5 整除。
        divisors[1] 的可整除性得分为 2 ，因为 nums[1] 和 nums[2] 都能被 7 整除。
        divisors[2] 的可整除性得分为 2 ，因为 nums[0] 和 nums[3] 都能被5整除。
        由于 divisors[0]、divisors[1] 和 divisors[2] 的可整除性得分都是最大的，因此，我们返回数值最小的一个，即 divisors[2] 。
    示例 3：
        输入：nums = [12], divisors = [10,16]
        输出：10
        解释：divisors 中每个元素的可整除性得分为：
        divisors[0] 的可整除性得分为 0 ，因为 nums 中没有任何数字能被 10 整除。
        divisors[1] 的可整除性得分为 0 ，因为 nums 中没有任何数字能被 16 整除。
        由于 divisors[0] 和 divisors[1] 的可整除性得分都是最大的，因此，我们返回数值最小的一个，即 divisors[0] 。
    提示：
        1 <= nums.length, divisors.length <= 1000
        1 <= nums[i], divisors[i] <= 109
    Related Topics：
        数组
*/
public class NO2644_E_MaxDivScore {

    @Test
    public void test() {
        assert 5 == maxDivScore(
            new int[]{20, 14, 21, 10}, new int[]{5, 7, 5});
    }

    public int maxDivScore(int[] nums, int[] divisors) {
        int[][] arr = new int[divisors.length][2];
        for (int i = 0; i < divisors.length; i++) {
            int count = 0;
            for (int num : nums) {
                if (num % divisors[i] == 0) {
                    count++;
                }
            }
            arr[i] = new int[]{divisors[i], count};
        }
        Arrays.sort(arr, (a, b) -> b[1] - a[1]);
        Arrays.sort(arr, (a,b) ->{
            if(a[1] == b[1]){
                return a[0] - b[0];
            }else {
                return b[1] - a[1];
            }
        });

        return arr[0][0];
    }

}
