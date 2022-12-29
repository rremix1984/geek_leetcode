/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

/**
    (中等)
    287. 寻找重复数
        给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
        假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
        你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
    示例 1：
        输入：nums = [1,3,4,2,2]
        输出：2
    示例 2：
        输入：nums = [3,1,3,4,2]
        输出：3
    提示：
        1 <= n <= 105
        nums.length == n + 1
        1 <= nums[i] <= n
        nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
*/
public class NO287_N_FindDuplicate {

    @Test
    public void test() {
        assert 2 == findDuplicate(new int[]{1, 3, 4, 2, 2});
        assert 3 == findDuplicate(new int[]{3, 1, 3, 4, 2});
    }

    public int findDuplicate(int[] nums) {
        int l = 1;
        int r = nums.length - 1;
        int ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int cnt = 0;
            for (int num : nums)
                if (num <= mid)
                    cnt++;

            if (cnt <= mid) {
                l = mid + 1;
            } else {
                r = mid - 1;
                ans = mid;
            }
        }
        return ans;
    }

}
