/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
    [ARRAY] |
    (简单)
    2239. 找到最接近 0 的数字
        给你一个长度为n的整数数组nums，请你返回nums中最接近0的数字。
        如果有多个答案，请你返回它们中的最大值。
    示例 1：
        输入：nums = [-4, -2, 1, 4, 8]
        输出：1
        解释：-4 到 0 的距离为 |-4| = 4 。
             -2 到 0 的距离为 |-2| = 2 。
             1 到 0 的距离为 |1| = 1 。
             4 到 0 的距离为 |4| = 4 。
             8 到 0 的距离为 |8| = 8 。
             所以，数组中距离 0 最近的数字为 1 。
    示例 2：
        输入：nums = [2, -1, 1]
        输出：1
        解释：1 和 -1 都是距离 0 最近的数字，所以返回较大值 1 。
*/
public class NO2239_E_FindClosestNumber {

    @Test
    public void test() {
        assert  1 == findClosestNumber(new int[]{-4, -2, 1, 4, 8});
        assert  1 == findClosestNumber(new int[]{2, -1, 1});
        assert -1 == findClosestNumber(new int[]{2, -1, 3});
    }

    public int findClosestNumber(int[] nums) {
        // 2024/3/10 NO.1 还是需要思考一下才能做出来
        return -1;
    }

}














/*
// 方法1：
public int findClosestNumber(int[] nums) {
    int res = nums[0];
    for (int n : nums)
        if (abs(n) == abs(res))
            res = max(n, res);
        else if (abs(n) < abs(res))
            res = n;
    return res;
}
*/