/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.SwapUtil.swap;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    NO.283 Move Zeroes 移动零
        给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，
        同时保持非零元素的相对顺序。请注意，必须在不复制数组的情况下原地对数组进行操作。
    示例 1:
        输入: nums = {0, 1, 0, 3, 12}
        输出: [1, 3, 12, 0, 0]
 */
public class NO283_E_MoveZeroes_x3 {

    @Test
    public void test() {
        int[] input = new int[]{0, 1, 0, 3, 12};
        moveZeroes(input);
        assertArrayEquals(new int[]{1, 3, 12, 0, 0}, input);
    }

    public void moveZeroes(int nums[]) {

    }

}





















/**
// 方法1：
public void moveZeroes(int nums[]) {
    int j = 0;
    for (int i = 0; i < nums.length; i++)
        if (nums[i] != 0) {
            nums[j] = nums[i];
            if (i != j)
                nums[i] = 0;
            j++;
      }
}
*/