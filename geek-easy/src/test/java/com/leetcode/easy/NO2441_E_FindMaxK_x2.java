/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static java.lang.Math.abs;

/**
    [ARRAY]
    (简单)
    2441. 与对应负数同时存在的最大正整数
        给你一个不包含任何零的整数数组 nums ，找出自身与对应的负数都在数组中存在的最大正整数 k 。
        返回正整数 k ，如果不存在这样的整数，返回 -1 。
    示例 1：
        输入：nums = {-1, 2, -3, 3}
        输出：3
        解释：3 是数组中唯一一个满足题目要求的 k 。
    示例 2：
        输入：nums = {-1, 10, 6, 7, -7, 1}
        输出：7
        解释：数组中存在 1 和 7 对应的负数，7 的值更大。
    示例 3：
        输入：nums = {-10, 8, 6, 7, -2, -3}
        输出：-1
        解释：不存在满足题目要求的 k ，返回 -1 。
*/
public class NO2441_E_FindMaxK_x2 {

    @Test
    public void test() {
        assert 3 == findMaxK(new int[]{-1, 2, -3, 3});
        assert 7 == findMaxK(new int[]{-1, 10, 6, 7, -7, 1});
        assert -1 == findMaxK(new int[]{-10, 8, 6, 7, -2, -3});
    }

    public int findMaxK(int[] nums) {
        int max = -1;
        return max;
    }

}
















/**
public int findMaxK(int[] nums) {
    int ans = -1;
    // 使用哈希set记录出现过的数字
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
        int abs = abs(num);
        // 出现过相反数，且绝对值大于当前最大值
        if (set.contains(-num) && abs > ans)
            // 更新最大值
            ans = abs;

        set.add(num);
    }
    return ans;
}
*/