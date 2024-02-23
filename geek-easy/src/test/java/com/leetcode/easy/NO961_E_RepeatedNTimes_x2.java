/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
    [ARRAY]
    (简单)
    961. 在长度 2N 的数组中找出重复 N 次的元素
        给你一个整数数组 nums ，该数组具有以下属性：
            1）nums.length == 2 * n.
            2）nums 包含 n + 1 个 不同的 元素
            3）nums 中恰有一个元素重复 n 次
        找出并返回重复了 n 次的那个元素。
    示例 1：
        输入：nums = [1, 2, 3, 3]
        输出：3
    示例 2：
        输入：nums = [2, 1, 2, 5, 3, 2]
        输出：2
    示例 3：
        输入：nums = [5, 1, 5, 2, 5, 3, 5, 4]
        输出：5
    提示：
        2 <= n <= 5000
        nums.length == 2 * n
        0 <= nums[i] <= 104
        nums 由 n + 1 个 不同的 元素组成，且其中一个元素恰好重复 n 次

    方法一：哈希表
        思路与算法
    记重复n次的元素为x。由于数组nums中有n + 1个不同的元素，而其长度为2n，
    那么数组中【剩余的元素均只出现了一次】。也就是说，我们只需要找到重复出现的元素即为答案。
    因此我们可以对数组进行一次遍历，并使用哈希集合存储已经出现过的元素。如果遍历到了哈
    希集合中的元素，那么返回该元素作为答案。
*/
public class NO961_E_RepeatedNTimes_x2 {

    @Test
    public void test() {
        assert 3 == repeatedNTimes(new int[]{1, 2, 3, 3});
        assert 2 == repeatedNTimes(new int[]{2, 1, 2, 5, 3, 2});
        assert 5 == repeatedNTimes(new int[]{5, 1, 5, 2, 5, 3, 5, 4});
    }

    public int repeatedNTimes(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums)
            if (!set.add(num))
                return num;

        // 不可能的情况
        return -1;
    }

}


















/**
// 方法1：脑筋急转弯
public int repeatedNTimes(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums)
        if (!set.add(num))
            return num;

    // 不可能的情况
    return -1;
}
*/