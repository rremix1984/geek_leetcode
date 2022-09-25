/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;
import static com.leetcode.util.MathUtils.getArrays;
import static java.lang.Math.max;

/**
    (简单)
    剑指 Offer II 101. 分割等和子集
        给定一个非空的正整数数组 nums ，请判断能否将这些数字分成元素和相等的两部分。
    示例 1：
        输入：nums = {1, 5, 11, 5}
        输出：true
        解释：nums 可以分割成 {1, 5, 5} 和 {11} 。
    示例 2：
        输入：nums = {1, 2, 3, 5}
        输出：false
        解释：nums 不可以分为和相等的两部分
*/
public class OfferII_101_E_CanPartition {

    @Test
    public void test() {
        assert canPartition(getArrays(1, 5, 11, 5));
        assert !canPartition(getArrays(1, 2, 3, 5));
    }

    public boolean canPartition(int[] nums) {
        return false;
    }

}
















/**
// 方法1：
public boolean canPartition(int[] nums) {
    if (nums.length < 2)
        return false;

    int sum = 0;
    int maxNum = 0;
    for (int num : nums) {
        sum += num;
        maxNum = max(maxNum, num);
    }

    if (sum % 2 != 0)
        return false;

    int target = sum / 2;
    if (maxNum > target)
        return false;

    boolean[] dp = new boolean[target + 1];
    dp[0] = true;

    for (int num : nums)
        for (int j = target; j >= num; j--)
            dp[j] |= dp[j - num];

    return dp[target];
}


// 方法2：动态规划 dp
public boolean canPartition(int[] nums) {
    if (nums.length < 2)
        return false;

    int sum = 0;
    int maxNum = 0;
    for (int num : nums) {
        sum += num;
        maxNum = Math.max(maxNum, num);
    }

    if (sum % 2 != 0)
        return false;

    int target = sum / 2;
    if (maxNum > target)
        return false;

    boolean[][] dp = new boolean[nums.length][target + 1];
    for (int i = 0; i < nums.length; i++)
        dp[i][0] = true;

    dp[0][nums[0]] = true;
    for (int i = 1; i < nums.length; i++) {
        int num = nums[i];
        for (int j = 1; j <= target; j++)
            if (j >= num)
                dp[i][j] = dp[i - 1][j] | dp[i - 1][j - num];
            else
                dp[i][j] = dp[i - 1][j];
    }
    return dp[nums.length - 1][target];
}
*/