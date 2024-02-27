/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    2404. 出现最频繁的偶数元素
        给你一个整数数组 nums ，返回出现最频繁的偶数元素。
        如果存在多个满足条件的元素，只需要返回最小的一个。如果不存在这样的元素，返回 -1 。
    示例 1：
        输入：nums = {0, 1, 2, 2, 4, 4, 1}
        输出：2
        解释：
        数组中的偶数元素为 0、2 和 4 ，在这些元素中，2 和 4 出现次数最多。
        返回最小的那个，即返回 2 。
    示例 2：
        输入：nums = {4, 4, 4, 9, 2, 4}
        输出：4
        解释：4 是出现最频繁的偶数元素。
    示例 3：
        输入：nums = {29, 47, 21, 41, 13, 37, 25, 7}
        输出：-1
        解释：不存在偶数元素。

    提示：
        1 <= nums.length <= 2000
        0 <= nums[i] <= 10^5
*/
public class NO2404_E_MostFrequentEven {

    @Test
    public void test() {
        assert 2 == mostFrequentEven(new int[]{0, 1, 2, 2, 4, 4, 1});
        assert 4 == mostFrequentEven(new int[]{4, 4, 4, 9, 2, 4});
        assert -1 == mostFrequentEven(new int[]{29, 47, 21, 41, 13, 37, 25, 7});
    }

    public int mostFrequentEven(int[] nums) {
        // 偶数出现的最大频次，最大频次中最小的偶数
        // 如果没有返回 -1
        // 2024/2/27 NO.3
        int res = -1;
        return res;
    }

}














/**
// 方法1：
public int mostFrequentEven(int[] nums) {
    // 偶数出现的最大频次，最大频次中最小的偶数
    int res = -1;

    // 哈希表 hash[i]代表i出现的次数cnt
    // 0 <= nums[i] <= 10^5
    int[] dict = new int[100001];

    // 不论奇偶的最大数
    int maxCnt = 0;

    for (int i : nums)
        // 必须是一个偶数
        if (i % 2 == 0) {
            dict[i]++;
            if (dict[i] > maxCnt) {
                maxCnt = dict[i];
                res = i;
            } else if (dict[i] == maxCnt) {
                res = min(res,i);
            }
        }
    return res;
}
*/