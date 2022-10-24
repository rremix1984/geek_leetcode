/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;

/**
    (简单)
    908. 最小差值 I
        给你一个整数数组 nums，和一个整数 k 。
        在一个操作中，您可以选择 0 <= i < nums.length 的任何索引 i 。
        将 nums[i] 改为 nums[i] + x ，其中 x 是一个范围为 [-k, k] 的整数。
        对于每个索引 i ，最多 只能 应用 一次 此操作。
        nums 的 分数 是 nums 中最大和最小元素的差值。
        在对 nums 中的每个索引最多应用一次上述操作后，返回 nums 的最低 分数 。
    示例 1：
        输入：nums = {1}, k = 0
        输出：0
        解释：分数是 max(nums) - min(nums) = 1 - 1 = 0。
    示例 2：
        输入：nums = {0, 10}, k = 2
        输出：6
        解释：将 nums 改为 {2, 8}。分数是 max(nums) - min(nums) = 8 - 2 = 6。
    示例 3：
        输入：nums = {1, 3, 6}, k = 3
        输出：0
        解释：将 nums 改为 {4, 4, 4}。分数是 max(nums) - min(nums) = 4 - 4 = 0。
    提示：
        1 <= nums.length <= 104
        0 <= nums[i] <= 104
        0 <= k <= 104

    方法一：数学
        思路与算法
        假设整数数组 nums 的最小值为 minNum，最大值为 maxNum。
    1）如果 maxNum − minNum ≤ 2k，那么我们总可以将整数数组 nums 的所有元素都改为同一个整数，
       因此更改后的整数数组 nums 的最低分数为 0。
       证明：因为 maxNum − minNum ≤ 2k，所以存在整数 x ∈ [minNum,maxNum]，使得
       x − minNum≤k 且 maxNum − x ≤ k。那么整数数组 nums 的所有元素与整数 x 的绝对差值都不超过 k，
       即所有元素都可以改为 x。

    2）如果 maxNum − minNum > 2k，那么更改后的整数数组 nums 的最低分数为 maxNum−minNum−2k。
       证明：对于 minNum 和 maxNum 两个元素，我们将 minNum 改为 minNum + k，
       maxNum 改为 maxNum − k，此时两个元素的绝对差值最小。因此更改后的整数数组 nums 的最低分数大于等于
       maxNum − minNum − 2k。
       对于整数数组 nums 中的元素 x，如果 x < minNum + k，那么 x 可以更改为 minNum + k，如果
       x > maxNum − k，那么 x 可以更改为 maxNum − k，因此整数数组 nums 的所有元素都可以改为区间
       [minNum + k,maxNum − k] 的整数，所以更改后的整数数组 nums 的最低分数小于等于 maxNum − minNum − 2k。
       综上所述，更改后的整数数组 nums 的最低分数为 maxNum − minNum − 2k。
 */
public class NO908_E_SmallestRangeI_x2 {

    @Test
    public void test() {
        assert 0 == smallestRangeI(new int[]{1}, 0);
        assert 6 == smallestRangeI(new int[]{0, 10}, 2);
        assert 0 == smallestRangeI(new int[]{1, 3, 6},3);
    }

    public int smallestRangeI(int[] nums, int k) {
        return -1;
    }

}


















/**
// 方法1：
public int smallestRangeI(int[] nums, int k) {
    int min = Arrays.stream(nums).min().getAsInt();
    int max = Arrays.stream(nums).max().getAsInt();
    return max(max - min - 2 * k, 0);
}
*/