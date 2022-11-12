/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

/**
    (中等)
    128. 最长连续序列
        给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
        请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
    示例 1：
        输入：nums = [100, 4, 200, 1, 3, 2]
        输出：4
        解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
    示例 2：
        输入：nums = [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]
        输出：9
    提示：
        0 <= nums.length <= 10 ^ 5
        -10 ^ 9 <= nums[i] <= 10 ^ 9
*/
public class NO128_N_LongestConsecutive_x2 {

    @Test
    public void test() {
        assert 4 == longestConsecutive(
                new int[]{100, 4, 200, 1, 3, 2});
        assert 9 == longestConsecutive(
                new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1});
    }

    public int longestConsecutive(int[] nums) {
        int ans = 0;
        return ans;
    }

}


















/**
// 方法1：
public int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums)
        set.add(num);

    int ans = 0;
    for (int num : set) {
        // num是第一个元素，向前计算的话会重复，因此要跳过
        if (set.contains(num - 1))
            continue;

        int cnt = 1;
        while (set.contains(num + 1)) {
            num++;
            cnt++;
        }
        ans = max(ans, cnt);
    }
    return ans;
}
*/