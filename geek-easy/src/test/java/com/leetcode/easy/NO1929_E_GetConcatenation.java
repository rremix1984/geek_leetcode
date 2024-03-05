/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)
    1929. 数组串联
        给你一个长度为n的整数数组nums。请你构建一个长度为2n的
        答案数组ans，数组下标从0开始计数，对于所有0<=i<n的i，
        满足下述所有要求：
            1）ans[i]==nums[i]
            2）ans[i+n]==nums[i]
        具体而言，ans 由两个 nums 数组 串联 形成。
        返回数组ans。
    示例 1：
        输入：nums = [1,2,1]
        输出：[1,2,1,1,2,1]
        解释：数组 ans 按下述方式形成：
        - ans = [nums[0],nums[1],nums[2],nums[0],nums[1],nums[2]]
        - ans = [1,2,1,1,2,1]
    示例 2：
        输入：nums = [1,3,2,1]
        输出：[1,3,2,1,1,3,2,1]
        解释：数组 ans 按下述方式形成：
        - ans = [nums[0],nums[1],nums[2],nums[3],nums[0],nums[1],nums[2],nums[3]]
        - ans = [1,3,2,1,1,3,2,1]
*/
public class NO1929_E_GetConcatenation {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 3, 2, 1, 1, 3, 2, 1},
            getConcatenation(new int[]{1, 3, 2, 1}));
        assertArrayEquals(new int[]{1, 2, 1, 1, 2, 1},
            getConcatenation(new int[]{1, 2, 1}));
    }

    public int[] getConcatenation(int[] nums) {
        // 2024/2/27 NO.3
        return null;
    }

}

















/*
// 方法1：
public int[] getConcatenation(int[] nums) {
    //1.创建长度为2n的数组ans，n为nums数组的长度
    int[] ans = new int[nums.length * 2];

    //2.遍历nums数组，为它赋值
    for (int i = 0; i < nums.length; i++) {
        ans[i] = nums[i];
        ans[i + nums.length] = nums[i];
    }
    return ans;
}

// 方法2：
public int[] getConcatenation(int[] nums) {
    int[] ans = new int[nums.length * 2];
    for (int i = 0; i < ans.length; i++)
        ans[i] = nums[i % nums.length];
    return ans;
}
*/