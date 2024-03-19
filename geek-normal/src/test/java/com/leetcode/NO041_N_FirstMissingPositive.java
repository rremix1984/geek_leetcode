/**
 * copyright@2019/12/23 lyc
 */
package com.leetcode;

import org.junit.Test;
import static java.lang.Math.abs;

/**
    [ARRAY]
    (中等)
    NO.041 缺失的第一个正整数
    给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
    请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
    示例 1：
        输入：nums = [1, 2, 0]
        输出：3
        解释：范围 [1,2] 中的数字都在数组中。
    示例 2：
        输入：nums = [3, 4, -1, 1]
        输出：2
        解释：1 在数组中，但 2 没有。
    示例 3：
        输入：nums = [7, 8, 9, 11, 12]
        输出：1
        解释：最小的正数 1 没有出现。
    提示：
        1 <= nums.length <= 105
        -231 <= nums[i] <= 231 - 1
    Related Topics:数组,哈希表
*/
public class NO041_N_FirstMissingPositive {

    @Test
    public void test() {
        assert 3 == firstMissingPositive(new int[]{1, 2, 0});
        assert 2 == firstMissingPositive(new int[]{3, 4, -1, 1});
        assert 1 == firstMissingPositive(new int[]{7, 8, 9, 11, 12});
    }

    public int firstMissingPositive(int[] nums) {
        // 2024/3/19 NO.1
        int n = nums.length;
        for (int i = 0; i < n; i++)
            if (nums[i] <= 0)
                nums[i] = n + 1;

        for (int i = 0; i < n; i++) {
            int num = abs(nums[i]);
            if (num <= n)
                nums[num - 1] = -abs(nums[num - 1]);
        }

        for (int i = 0; i < n; i++)
            if (nums[i] > 0)
                return i + 1;

        return n + 1;
    }

}
