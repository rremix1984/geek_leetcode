/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

/**
    (中等)
    81. 搜索旋转排序数组 II
        已知存在一个按非降序排列的整数数组nums，数组中的值不必互不相同。
        在传递给函数之前，nums在预先未知的某个下标 k（0 <= k < nums.length）
        上进行了旋转，使数组变为
            [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
        例如，[0,1,2,4,4,4,5,6,6,7]在下标5处经旋转后可能变为[4,5,6,6,7,0,1,2,4,4]。
        给你旋转后的数组nums和一个整数target，请你编写一个函数来判断给定的
        目标值是否存在于数组中。如果nums中存在这个目标值target，则返回true，否则返回false。
        你必须尽可能减少整个操作步骤。
    示例 1：
        输入：nums = [2,5,6,0,0,1,2], target = 0
        输出：true
    示例 2：
        输入：nums = [2,5,6,0,0,1,2], target = 3
        输出：false
    提示：
        1 <= nums.length <= 5000
        -104 <= nums[i] <= 104
        题目数据保证 nums 在预先未知的某个下标上进行了旋转
        -104 <= target <= 104
*/
public class NO081_N_Search_x2 {

    @Test
    public void test() {
        assert  search(new int[]{2, 5, 6, 0, 0, 1, 2}, 0);
        assert !search(new int[]{2, 5, 6, 0, 0, 1, 2}, 3);
        assert  search(new int[]{1, 0, 1, 1, 1}, 0);
        assert !search(new int[]{1}, 0);
    }

    public boolean search(int[] nums, int target) {
        return false;
    }

}

















/**
// 方法1：
public boolean search(int[] nums, int target) {
    int n = nums.length;
    if (n == 0)
        return false;

    int l = 0;
    int r = n - 1;
    while (l <= r) {
        int mid = (l + r) / 2;
        if (nums[mid] == target)
            return true;

        // 因为全部元素可能都相等，所以需要把重复的元素（[l] == [mid] == [r]）去掉
        // 左、右都等于中间元素, 则l、r向中间走
        if (nums[l] == nums[mid] && nums[mid] == nums[r]) {
            ++l;
            --r;
            // 左边有序
        } else if (nums[l] < nums[mid]) {
            // 查找的元素在左侧
            if (nums[l] <= target && target < nums[mid])
                r = mid - 1;
                // 查找的元素在右侧
            else
                l = mid + 1;
            // 右边有序
        } else {
            // 查找的元素在右侧
            if (nums[mid] < target && target <= nums[n - 1])
                l = mid + 1;
                // 查找的元素在左侧
            else
                r = mid - 1;
        }
    }
    return false;
}
*/