/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static java.lang.Math.max;

/**
    [ARRAY]
    (简单)
    485.最大连续 1 的个数
        给定一个二进制数组 nums，计算其中最大连续 1 的个数。
    示例 1：
        输入：nums = {1, 1, 0, 1, 1, 1}
        输出：3
        解释：开头的两位和最后的三位都是连续 1 ，所以最大连续 1 的个数是 3.
    示例 2:
        输入：nums = {1, 0, 1, 1, 0, 1}
        输出：2
    提示：
        1 <= nums.length <= 105
        nums[i] 不是 0 就是 1.
*/
public class NO485_E_FindMaxConsecutiveOnes_x2 {

    @Test
    public void test() {
        assert 3 == findMaxConsecutiveOnes(new int[]{1, 1, 0, 1, 1, 1});
        assert 2 == findMaxConsecutiveOnes(new int[]{1, 0, 1, 1, 0, 1});
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        return max;
    }

}

















/**
// 方法1：
public int findMaxConsecutiveOnes(int[] nums) {
    int max = 0;
    int cnt = 0;
    for (int n : nums)
        if (n == 0)
            cnt = 0;
        else
            max = max(max, ++cnt);
    return max;
}
*/