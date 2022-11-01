/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
    (简单)
    915. 分割数组
        给定一个数组 nums ，将其划分为两个连续子数组 left 和 right， 使得：
            1）left 中的每个元素都小于或等于 right 中的每个元素。
            2）left 和 right 都是非空的。
            3）left 的长度要尽可能小。
        在完成这样的分组后返回 left 的 长度 。
        用例可以保证存在这样的划分方法。
    示例 1：
        输入：nums = {5, 0, 3, 8, 6}
        输出：3
        解释：left = {5, 0, 3}，right = {8, 6}
    示例 2：
        输入：nums = {1, 1, 1, 0, 6, 12}
        输出：4
        解释：left = {1, 1, 1, 0}，right = {6, 12}
    提示：
        2 <= nums.length <= 105
        0 <= nums[i] <= 106
        可以保证至少有一种方法能够按题目所描述的那样对 nums 进行划分。

    方法二：一次遍历
        思路和算法
        假设我们预先规定了一个 left 的划分，其最大值为 maxLeft，划分位置为 leftPos，
        表示 nums[0,leftPos] 都属于 left。如果 leftPos 右侧所有元素都大于等于它，
        那么该划分方案是合法的。
        但如果我们找到 nums[i]，其中 i > leftPos，并且 nums[i] < maxLeft，
        那么意味着 leftPos 作为划分位置是非法的，需要更新 leftPos = i，以及
        maxLeft = 0 max i nums[i]。
        因此，我们首先初始化 maxLeft = nums[0]，leftPos=0，然后在 [1,n−2] 范围内从小到大遍历i，
        过程中维护一个变量 curMax，它的值是 0 max i nums[i]。
        此时如果有 nums[i]<maxLeft，就按照上述方法更新。
        最终遍历结束时，答案就是 leftPos+1。
*/
public class NO915_N_PartitionDisjoint {

    @Test
    public void test() {
        assert 3 == partitionDisjoint(new int[]{5, 0, 3, 8, 6});
        assert 4 == partitionDisjoint(new int[]{1, 1, 1, 0, 6, 12});
    }

    public int partitionDisjoint(int[] nums) {
        int leftMax = nums[0];
        int leftPos = 0;
        int curMax = nums[0];
        for (int i = 1; i < nums.length - 1; i++) {
            curMax = max(curMax, nums[i]);
            if (nums[i] < leftMax) {
                leftMax = curMax;
                leftPos = i;
            }
        }
        return leftPos + 1;
    }

}


















/**
// 方法1：
public int partitionDisjoint(int[] nums) {
    int n = nums.length;
    int[] right = new int[n];
    right[n - 1] = nums[n - 1];
    for (int i = n - 2; i >= 0; i--)
        right[i] = min(nums[i], right[i + 1]);

    int left = 0;
    for (int i = 0; i < n - 1; i++) {
        left = max(left, nums[i]);
        // left的最大值小于right的最小值
        if (left <= right[i + 1])
            return i + 1;
    }
    return n - 1;
}
*/