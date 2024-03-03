/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    1995. 统计特殊四元组
        给你一个下标从0开始的整数数组nums，返回满足下述条件的
        不同四元组 (a, b, c, d) 的数目：
        nums[a] + nums[b] + nums[c] == nums[d]，且 a < b < c < d
    示例 1：
        输入：nums = [1, 2, 3, 6]
        输出：1
        解释：满足要求的唯一一个四元组是 (0, 1, 2, 3) 因为 1 + 2 + 3 == 6 。
    示例 2：
        输入：nums = [3, 3, 6, 4, 5]
        输出：0
        解释：[3, 3, 6, 4, 5] 中不存在满足要求的四元组。
    示例 3：
        输入：nums = [1, 1, 1, 3, 5]
        输出：4
        解释：满足要求的 4 个四元组如下：
        - (0, 1, 2, 3): 1 + 1 + 1 == 3
        - (0, 1, 3, 4): 1 + 1 + 3 == 5
        - (0, 2, 3, 4): 1 + 1 + 3 == 5
        - (1, 2, 3, 4): 1 + 1 + 3 == 5
*/
public class NO1995_E_CountQuadruplets {

    @Test
    public void test() {
        assert 1 == countQuadruplets(new int[]{1, 2, 3, 6});
        assert 0 == countQuadruplets(new int[]{3, 3, 6, 4, 5});
        assert 4 == countQuadruplets(new int[]{1, 1, 1, 3, 5});
    }

    public int countQuadruplets(int[] nums) {
        int ans = 0;
        return ans;
    }

}
















/**
// 方法1：暴力破解
public int countQuadruplets(int[] nums) {
    int ans = 0;
    for (int a = 0; a < nums.length; a++)
        for (int b = a + 1; b < nums.length; b++)
            for (int c = b + 1; c < nums.length; c++)
                for (int d = c + 1; d < nums.length; d++)
                    if (nums[a] + nums[b] + nums[c] == nums[d])
                        ans++;
    return ans;
}


// 方法2：
public int countQuadruplets(int[] nums) {
    int ans = 0;
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = nums.length - 2; i >= 2; i--) {
        map.put(nums[i + 1], map.getOrDefault(nums[i + 1], 0) + 1);
        for (int a = 0; a < i; a++)
            for (int b = a + 1; b < i; b++)
                ans += map.getOrDefault(nums[a] + nums[b] + nums[i], 0);

    }
    return ans;
}
*/