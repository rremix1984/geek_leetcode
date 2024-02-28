/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.MathUtils.max;
import static java.lang.Math.min;

/**
    [ARRAY] |
    (简单)
    2293. 极大极小游戏
        给你一个下标从0开始的整数数组nums，其长度是2的幂。
        对nums执行下述算法：
         1）设n等于nums的长度，如果n==1，终止算法过程。否则，创建一个
            新的整数数组newNums，新数组长度为n/2，下标从0开始。
         2）对于满足0<=i<n/2的每个偶数下标i，
            将newNums[i]赋值为min(nums[2*i], nums[2*i+1])。
         3）对于满足0<=i<n/2的每个奇数下标i，
            将newNums[i]赋值为max(nums[2*i], nums[2*i+1])。
         4）用newNums替换nums。从步骤1开始重复整个过程。
            执行算法后，返回 nums 中剩下的那个数字。

    示例1：
        输入：nums=[1, 3, 5, 2, 4, 8, 2, 2]
        输出：1
        解释：重复执行算法会得到下述数组。
             第一轮：nums=[1, 5, 4, 2]
             第二轮：nums=[1, 4]
             第三轮：nums=[1]
             1是最后剩下的那个数字，返回1。
    示例2：
        输入：nums=[3]
        输出：3
        解释：3就是最后剩下的数字，返回 3 。
*/
public class NO2293_E_MinMaxGame {

    @Test
    public void test() {
        assert 1 == minMaxGame(
            new int[]{1, 3, 5, 2, 4, 8, 2, 2});
        assert 3 == minMaxGame(
            new int[]{3});
    }

    public int minMaxGame(int[] nums) {
        // 2024/2/27 NO.3
        int n = nums.length;
        return nums[0];
    }

}














/*
// 方法1：
public int minMaxGame(int[] nums) {
    int n = nums.length;
    // 重复整个过程，直到长度n==1为止
    while (n != 1) {
        int cnt = 0;
        // 遍历新的数组
        for (int i = 0; i < n; i += 2) {
            // 原地变换
            if (cnt % 2 == 0)
                nums[cnt] = min(nums[i], nums[i + 1]);
            else
                nums[cnt] = max(nums[i], nums[i + 1]);
            cnt++;
        }
        // 更新长度
        n /= 2;
    }
    // 由于原地变换，最后返回数组首元素
    return nums[0];
}
*/