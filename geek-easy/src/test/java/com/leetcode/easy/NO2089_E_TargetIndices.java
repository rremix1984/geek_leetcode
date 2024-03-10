/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static java.util.Collections.emptyList;

/**
    [ARRAY] |
    (简单)
    2089. 找出数组排序后的目标下标
        给你一个下标从0开始的整数数组nums以及一个目标元素target。
        目标下标是一个满足nums[i] == target的下标i。
        将nums按【非递减】顺序排序后，返回由nums中目标下标组成的列表。
        如果不存在目标下标，返回一个【空】列表。返回的列表必须按【递增】顺序排列。
    示例 1：
        输入：nums = {1, 2, 5, 2, 3},  target = 2
        输出：{1, 2}
        解释：排序后，nums 变为 {1, 2, 2, 3, 5} 。
        满足 nums{i} == 2 的下标是 1 和 2 。
    示例 2：
        输入：nums = {1, 2, 5, 2, 3},  target = 3
        输出：{3}
        解释：排序后，nums 变为 {1, 2, 2, 3, 5} 。
        满足 nums{i} == 3 的下标是 3 。
    示例 3：
        输入：nums = {1, 2, 5, 2, 3},  target = 5
        输出：{4}
        解释：排序后，nums 变为 {1, 2, 2, 3, 5} 。
        满足 nums{i} == 5 的下标是 4 。
    示例 4：
        输入：nums = {1, 2, 5, 2, 3},  target = 4
        输出：{}
        解释：nums 中不含值为 4 的元素。
*/
public class NO2089_E_TargetIndices {

    @Test
    public void test() {
        assert getArray(1, 2).equals(targetIndices(
                new int[]{1, 2, 5, 2, 3},2));
        assert getArray(3).equals(targetIndices(
                new int[]{1, 2, 5, 2, 3},3));
        assert getArray(4).equals(targetIndices(
                new int[]{1, 2, 5, 2, 3},5));
        assert emptyList().equals(targetIndices(
                new int[]{1, 2, 5, 2, 3},4));
    }

    public List<Integer> targetIndices(int[] nums, int target) {
        // 2024/3/10 NO.1
        List<Integer> res = new ArrayList<>();
        return res;
    }

}
















/*
// 方法1：
public List<Integer> targetIndices(int[] nums, int target) {
    List<Integer> res = new ArrayList<>();

    // 小于 target 的元素数量
    int cnt1 = 0;

    // 等于 target 的元素数量
    int cnt2 = 0;

    for (int num : nums)
        if (num < target)
            cnt1++;
        else if (num == target)
            cnt2++;

    for (int i = cnt1; i < cnt1 + cnt2; i++)
        res.add(i);

    return res;
}
*/