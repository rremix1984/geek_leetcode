/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

/**
    (中等)
    209. 长度最小的子数组
        给定一个含有 n 个正整数的数组和一个正整数 target。
        找出该数组中满足其和 ≥ target 的长度最小的连续子数组
            [numsl, numsl+1, ..., numsr-1, numsr]，
        并返回其长度。如果不存在符合条件的子数组，返回 0。
    示例 1：
        输入：target = 7, nums = [2, 3, 1, 2, 4, 3]
        输出：2
        解释：子数组 [4, 3] 是该条件下的长度最小的子数组。
    示例 2：
        输入：target = 4, nums = [1, 4, 4]
        输出：1
    示例 3：
        输入：target = 11, nums = [1, 1, 1, 1, 1, 1, 1, 1]
        输出：0
    提示：
        1 <= target <= 10 ^ 9
        1 <= nums.length <= 10 ^ 5
        1 <= nums[i] <= 10 ^ 5

    方法三：滑动窗口
        在方法一和方法二中，都是每次确定子数组的开始下标，然后得到长度最小的子数组，
    因此时间复杂度较高。为了降低时间复杂度，可以使用滑动窗口的方法。定义两个指针 start 和 end
    分别表示子数组（滑动窗口窗口）的开始位置和结束位置，维护变量 sum 存储子数组中的元素和
    （即从 nums[start] 到 nums[end] 的元素和）。
    初始状态下，start 和 end 都指向下标0，sum 的值为 0。
    每一轮迭代，将 nums[end] 加到 sum，如果 sum ≥ s，则更新子数组的最小长度
    （此时子数组的长度是 end − start + 1），然后将 nums[start] 从 sum 中减去
    并将 start 右移，直到 sum < s，在此过程中同样更新子数组的最小长度。
    在每一轮迭代的最后，将 end 右移。
*/
public class NO209_N_MinSubArrayLen_x2 {

    @Test
    public void test() {
        assert 2 == minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});
        assert 1 == minSubArrayLen(4, new int[]{1, 4, 4});
        assert 0 == minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1});
    }

    public int minSubArrayLen(int target, int[] nums) {
        int ans = MAX_VALUE;
        return ans;
    }

}














/**
// 方法1：
public int minSubArrayLen(int target, int[] nums) {
    if (nums.length == 0)
        return 0;

    int l = 0, r = 0;
    int sum = 0;
    int ans = MAX_VALUE;
    while (r < nums.length) {
        sum += nums[r];
        while (sum >= target) {
            ans = min(ans, r - l + 1);
            sum -= nums[l];
            l++;
        }
        r++;
    }
    return ans == MAX_VALUE ? 0 : ans;
}
*/