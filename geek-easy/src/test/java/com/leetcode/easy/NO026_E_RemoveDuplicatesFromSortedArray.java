/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] ||
    (简单)
    26. 删除有序数组中的重复项
        给你一个【升序排列】的数组nums，请你【原地】删除重复出现的元素，
        使每个元素只出现一次，返回删除后数组的新长度。元素的相对顺序应该保持一致。
        由于在某些语言中不能改变数组的长度，所以必须将结果放在数组nums的第一部分。
        更规范地说，如果在删除重复项之后有k个元素，那么nums的前k个元素应该保存最终结果。
        将最终结果插入nums的前k个位置后返回k。
        不要使用额外的空间，你必须在【原地】修改输入数组并在使用O(1)额外空间的条件下完成。
    判题标准:
        系统会用下面的代码来测试你的题解:
        int[] nums = new int[]{1, 1, 2};      // 输入数组
        int[] expectedNums = new int[]{1, 2}; // 长度正确的期望答案
        int k = removeDuplicates(nums);       // 调用
        assert k == expectedNums.length;
        for (int i = 0; i < k; i++) {
            assert nums[i] == expectedNums[i];
        }
        如果所有断言都通过，那么您的题解将被 通过。
    示例 1：
        输入：nums = [1, 1, 2]
        输出：2, nums = [1, 2]
        解释：函数应该返回新的长度2，并且原数组nums的前两个元素被修改为1, 2。
             不需要考虑数组中超出新长度后面的元素。
    示例 2：
        输入：nums = [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
        输出：5, nums = [0, 1, 2, 3, 4]
        解释：函数应该返回新的长度5，并且原数组nums的前五个元素被修改为0, 1, 2, 3, 4。
             不需要考虑数组中超出新长度后面的元素。
*/
public class NO026_E_RemoveDuplicatesFromSortedArray {

    @Test
    public void test() {
        assert 0 == removeDuplicates(null);
        assert 2 == removeDuplicates(
            new int[]{1, 1, 2});
        assert 5 == removeDuplicates(
            new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4});
        assert 5 == removeDuplicates(
            new int[]{0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4});
    }

    public int removeDuplicates(int[] nums) {
        // 2024/2/20 NO.1 复习
        // 2024/3/4  NO.2 复习
        return -1;
    }

}



















/*
// 方法1：双指针法
public int removeDuplicates(int[] nums) {
    if (nums == null || nums.length == 0)
        return 0;

    int slow = 0;
    for (int fast = 1; fast < nums.length; fast++)
        if (nums[fast] != nums[slow])
            nums[++slow] = nums[fast];

    return slow + 1;
}
*/