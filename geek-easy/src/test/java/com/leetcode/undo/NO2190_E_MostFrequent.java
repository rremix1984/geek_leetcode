/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    2190. 数组中紧跟key之后出现最频繁的数字
        给你一个下标从0开始的整数数组nums，同时给你一个整数key，它在nums出现过。
        统计在nums数组中紧跟着key后面出现的不同整数target的出现次数。
        换言之，target 的出现次数为满足以下条件的 i 的数目：
            1）0 <= i <= n - 2
            2）nums[i] == key 且
            3）nums[i + 1] == target 。
        请你返回出现最多次数的target。测试数据保证出现次数最多的target是唯一的。
    示例 1：
        输入：nums = [1, 100, 200, 1, 100], key = 1
        输出：100
        解释：对于 target = 100 ，在下标 1 和 4 处出现过 2 次，且都紧跟着 key 。
             没有其他整数在 key 后面紧跟着出现，所以我们返回 100 。
    示例 2：
        输入：nums = [2, 2, 2, 2, 3], key = 2
        输出：2
        解释：对于target=2，在下标1，2和3处出现过3次，且都紧跟着key。
             对于target=3，在下标4出出现过1次，且紧跟着key。
             target=2是紧跟着key之后出现次数最多的数字，所以我们返回 2 。
*/
public class NO2190_E_MostFrequent {

    @Test
    public void test() {
        assert 100 == mostFrequent(
                new int[]{1, 100, 200, 1, 100},1);
        assert 2 == mostFrequent(
                new int[]{2, 2, 2, 2, 3},2);
    }

    public int mostFrequent(int[] nums, int key) {
        int ans = 0;
        return ans;
    }

}














/*
public int mostFrequent(int[] nums, int key) {
    int[] arr = new int[1001];
    int max = 0;
    int ans = 0;
    for (int i = 0; i < nums.length - 1; i++)
        if (nums[i] == key && ++arr[nums[i + 1]] > max) {
            max = arr[nums[i + 1]];
            ans = nums[i + 1];
        }

    return ans;
}
*/
