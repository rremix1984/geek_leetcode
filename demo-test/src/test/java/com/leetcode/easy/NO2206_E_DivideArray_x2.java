/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2206. 将数组划分成相等数对
        给你一个整数数组 nums ，它包含 2 * n 个整数。
        你需要将 nums 划分成 n 个数对，满足：
        每个元素 只属于一个 数对。
        同一数对中的元素 相等 。
        如果可以将 nums 划分成 n 个数对，请你返回 true ，否则返回 false 。
    示例 1：
        输入：nums = {3, 2, 3, 2, 2, 2}
        输出：true
        解释：nums 中总共有 6 个元素，所以它们应该被划分成 6 / 2 = 3 个数对。
             nums 可以划分成 (2,  2) ，(3,  3) 和 (2,  2) ，满足所有要求。
    示例 2：
        输入：nums = {1, 2, 3, 4}
        输出：false
        解释：无法将 nums 划分成 4 / 2 = 2 个数对且满足所有要求。

    提示：
        nums.length == 2 * n
        1 <= n <= 500
        1 <= nums[i] <= 500
*/
public class NO2206_E_DivideArray_x2 {

    @Test
    public void test() {
        assert divideArray(new int[]{3, 2, 3, 2, 2, 2});
        assert !divideArray(new int[]{1, 2, 3, 4});
    }

    public boolean divideArray(int[] nums) {
        int[] arr = new int[501];
        int cnt = 0;
        for (int num : nums)
            // 如果是偶数就 +1
            if ((++arr[num] & 1 )== 0)
                cnt++;
        return cnt * 2 == nums.length;
    }

}












/**
// 方法1：位运算法
public boolean divideArray(int[] nums) {
    int[] arr = new int[501];
    int cnt = 0;
    for (int num : nums)
        // 如果是偶数就 +1
        if ((++arr[num] & 1 )== 0)
            cnt++;
    return cnt * 2 == nums.length;
}
*/