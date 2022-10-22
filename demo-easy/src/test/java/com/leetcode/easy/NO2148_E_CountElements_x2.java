/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2148. 元素计数
        给你一个整数数组 nums ，统计并返回在 nums 中同时至少具有一个严格较小元素和一个严格较大元素的元素数目。
    示例 1：
        输入：nums = {11, 7, 2, 15}
        输出：2
        解释：元素 7 ：严格较小元素是元素 2 ，严格较大元素是元素 11 。
        元素 11 ：严格较小元素是元素 7 ，严格较大元素是元素 15 。
        总计有 2 个元素都满足在 nums 中同时存在一个严格较小元素和一个严格较大元素。
    示例 2：
        输入：nums = {-3, 3, 3, 90}
        输出：2
        解释：元素 3 ：严格较小元素是元素 -3 ，严格较大元素是元素 90 。
        由于有两个元素的值为 3 ，总计有 2 个元素都满足在 nums 中同时存在一个严格较小元素和一个严格较大元素。
*/
public class NO2148_E_CountElements_x2 {

    @Test
    public void test() {
        assert 2 == countElements(new int[]{11, 7, 2, 15});
        assert 2 == countElements(new int[]{-3, 3, 3, 90});
    }

    public int countElements(int[] nums) {
        int ans = 0;
        return ans;
    }

}















/**
// 方法1：
public int countElements(int[] nums) {
    int ans = 0;

    int max = nums[0];
    int min = nums[0];

    for (int num : nums) {
        max = max(max, num);
        min = min(min, num);
    }
    for (int num : nums)
        if (num > min && num < max)
            ans++;

    return ans;
}
*/