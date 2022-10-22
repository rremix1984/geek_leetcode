/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    645. 错误的集合
        集合 s 包含从 1 到 n 的整数。不幸的是，因为数据错误，
        导致集合里面某一个数字复制了成了集合里面的另外一个数字的值，
        导致集合 丢失了一个数字 并且 有一个数字重复 。
        给定一个数组 nums 代表了集合 S 发生错误后的结果。
        请你找出重复出现的整数，再找到丢失的整数，将它们以数组的形式返回。
    示例 1：
        输入：nums = [1, 2, 2, 4]
        输出：[2,3]
    示例 2：
        输入：nums = [1, 1]
        输出：[1, 2]
    提示：
        2 <= nums.length <= 104
        1 <= nums[i] <= 104
*/
public class NO645_E_FindErrorNums_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2, 3}, findErrorNums(new int[]{1, 2, 2, 4}));
        assertArrayEquals(new int[]{1, 2}, findErrorNums(new int[]{1, 1}));
    }

    public int[] findErrorNums(int[] nums) {
        if (nums.length == 0)
            return new int[0];

        int[] marked = new int[nums.length];
        for (int num : nums)
            marked[num - 1]++;

        int[] ret = new int[2];
        for (int i = 0; i < marked.length; i++)
            if (marked[i] == 2)
                ret[0] = i + 1;
            else if (marked[i] == 0)
                ret[1] = i + 1;

        return ret;
    }

}
