/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;

import static java.lang.Math.max;

/**
    (中等)
    剑指 Offer II 119. 最长连续序列
        给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
    示例 1：
        输入：nums = {100, 4, 200, 1, 3, 2}
        输出：4
        解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
    示例 2：
        输入：nums = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1}
        输出：9
*/
public class OfferII_119_N_LongestConsecutive_x2 {

    @Test
    public void test() {
        assert 4 == longestConsecutive(new int[]{100, 4, 200, 1, 3, 2});
        assert 9 == longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1});
    }

    public int longestConsecutive(int[] nums) {
        int res = 0;
        return res;
    }

}















/**
// 方法1：
public int longestConsecutive(int[] nums) {
    Set<Integer> s = new HashSet<>();
    for (int num : nums)
        s.add(num);

    int res = 0;
    for (int num : nums)
        // 连续元素断了，重头判断
        if (!s.contains(num - 1)) {
            int t = 1;
            int next = num + 1;
            // 能找到下一个【连续】元素 next++
            while (s.contains(next++))
                t++;

            res = Math.max(res, t);
        }
    return res;
}

// 方法2：
public int longestConsecutive(int[] nums) {
    int n = nums.length;
    if (n < 2) {
        return n;
    }
    Arrays.sort(nums);
    int res = 1, t = 1;
    for (int i = 1; i < n; ++i) {
        if (nums[i] == nums[i - 1]) {
            continue;
        }

        if (nums[i] - nums[i - 1] == 1) {
            t += 1;
            res = Math.max(res, t);
        } else {
            t = 1;
        }
    }
    return res;
}
*/