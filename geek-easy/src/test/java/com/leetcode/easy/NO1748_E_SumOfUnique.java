/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    1748. 唯一元素的和
        给你一个整数数组 nums 。数组中唯一元素是那些只出现 恰好一次 的元素。
        请你返回 nums 中唯一元素的 和 。
    示例 1：
        输入：nums = {1, 2, 3, 2}
        输出：4
        解释：唯一元素为 {1, 3} ，和为 4 。
    示例 2：
        输入：nums = {1, 1, 1, 1, 1}
        输出：0
        解释：没有唯一元素，和为 0 。
    示例 3 ：
        输入：nums = {1, 2, 3, 4, 5}
        输出：15
        解释：唯一元素为 {1, 2, 3, 4, 5} ，和为 15 。
*/
public class NO1748_E_SumOfUnique {

    @Test
    public void test() {
        assert  4 == sumOfUnique(new int[]{1, 2, 3, 2});
        assert  0 == sumOfUnique(new int[]{1, 1, 1, 1, 1});
        assert 15 == sumOfUnique(new int[]{1, 2, 3, 4, 5});
    }

    public int sumOfUnique(int[] nums) {
        // 2024/3/8 NO.1
        int ans = 0;
        return ans;
    }

}













/*
// 方法1：
public int sumOfUnique(int[] nums) {
    int[] count = new int[101];
    for (int num : nums)
        count[num]++;

    int ans = 0;
    for (int i = 0; i < count.length; i++)
        // 只出现一次的元素
        if (count[i] == 1)
            ans += i;

    return ans;
}
*/