package com.leetcode.normal;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static java.lang.Math.max;

/**
    [ARRAY]
    （简单）
    NO.3026 最大好子数组和
    给你一个长度为 n 的数组 nums 和一个 正 整数 k 。
    如果 nums 的一个子数组中，第一个元素和最后一个元素 差的绝对值恰好 为 k ，我们称这个子数组为 好 的。换句话说，如果子数组 nums[i..j] 满足 |nums[i] - nums[j]| == k ，那么它是一个好子数组。
    请你返回 nums 中 好 子数组的 最大 和，如果没有好子数组，返回 0 。
    示例 1：
        输入：nums = [1,2,3,4,5,6], k = 1
        输出：11
        解释：好子数组中第一个元素和最后一个元素的差的绝对值必须为 1 。好子数组有 [1,2] ，[2,3] ，[3,4] ，[4,5] 和 [5,6] 。最大子数组和为 11 ，对应的子数组为 [5,6] 。
    示例 2：
        输入：nums = [-1,3,2,4,5], k = 3
        输出：11
        解释：好子数组中第一个元素和最后一个元素的差的绝对值必须为 3 。好子数组有 [-1,3,2] 和 [2,4,5] 。最大子数组和为 11 ，对应的子数组为 [2,4,5] 。
    示例 3：
        输入：nums = [-1,-2,-3,-4], k = 2
        输出：-6
        解释：好子数组中第一个元素和最后一个元素的差的绝对值必须为 2 。好子数组有 [-1,-2,-3] 和 [-2,-3,-4] 。最大子数组和为 -6 ，对应的子数组为 [-1,-2,-3] 。
    提示：
        2 <= nums.length <= 105
        -109 <= nums[i] <= 109
        1 <= k <= 109
    Related Topics:数组,哈希表,前缀和
*/
public class NO3026_N_MaximumSubarraySum {

    @Test
    public void test() {
        assert 11 == maximumSubarraySum(
            new int[]{1,2,3,4,5,6}, 1);
    }

    public long maximumSubarraySum(int[] nums, int k) {
        //定义一个map，存储nums[i]对应的最小前缀和
        Map<Integer, Long> pre = new HashMap<>();
        long sum = 0; //当前累计元素值
        long maxV = Long.MIN_VALUE; //最大好子数组和
        boolean flag=true; //是否有好子数组

        //接下来去遍历，既计算前缀和也计算好子数组和
        for (int num : nums) {
            sum += num; //累加元素值
            Long orDefault = pre.getOrDefault(num, Long.MAX_VALUE); //看看map中是不是已经有了该值对应的前缀和了，如果有就比较大小替换成最小

            //最小前缀和（换成最小是为了使得子数组和最大）
            pre.put(num, Math.min(sum - num, orDefault));

            //sum实际上代表当前前缀和+一个元素。
            if (pre.containsKey(num - k)) {
                flag = false;
                //sum就是当前未知的前缀和加上nums[i]，完了去减掉我们要找的nums[i] - k对应的最小前缀和就有所求了。
                long nowV = sum - pre.get(num - k);

                //nums[i] - k是从左边慢慢计算的
                //这样搞就避免了二次循环。不然还得一个一个试加起来是不是k
                maxV = max(nowV, maxV);
            }

            if (pre.containsKey(num + k)) {
                flag = false;
                long nowV = sum - pre.get(num + k);
                maxV = max(nowV, maxV);
            }
        }
        return flag?0:maxV;
    }

}
