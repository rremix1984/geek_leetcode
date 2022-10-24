/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    303. 区域和检索 - 数组不可变
        给定一个整数数组 nums，处理以下类型的多个查询:
        计算索引left和right（包含 left 和 right）之间的nums元素的和，其中left <= right
        实现 NumArray 类：
            NumArray(int[] nums) 使用数组 nums 初始化对象
            int sumRange(int i, int j) 返回数组 nums 中索引 left 和 right 之间的元素的 总和 ，
        包含 left 和 right 两点（也就是 nums[left] + nums[left + 1] + ... + nums[right] )
    示例 1：
        输入：{"NumArray", "sumRange", "sumRange", "sumRange"}
             {{{-2, 0, 3, -5, 2, -1}}, {0, 2}, {2, 5}, {0, 5}}
        输出：{null, 1, -1, -3}
        解释：NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
             numArray.sumRange(0, 2); // return 1 ((-2) + 0 + 3)
             numArray.sumRange(2, 5); // return -1 (3 + (-5) + 2 + (-1))
             numArray.sumRange(0, 5); // return -3 ((-2) + 0 + 3 + (-5) + 2 + (-1))
    提示：
        1 <= nums.length <= 104
        -105 <= nums[i] <= 105
        0 <= i <= j < nums.length
        最多调用 104 次 sumRange 方法
*/
public class NO303_E_NumArray_x2 {

    @Test
    public void test() {
        NumArray numArray = new NumArray(new int[]{-2, 0, 3, -5, 2, -1});
        assert  1 == numArray.sumRange(0, 2); // return  1 ((-2) + 0 + 3)
        assert -1 == numArray.sumRange(2, 5); // return -1 (3 + (-5) + 2 + (-1))
        assert -3 == numArray.sumRange(0, 5); // return -3 ((-2) + 0 + 3 + (-5) + 2 + (-1))
    }

    class NumArray {

        public NumArray(int[] nums) {

        }

        public int sumRange(int i, int j) {
            return -1;
        }

    }

}
















/**
// 方法1：
class NumArray {

    int[] sums;
    public NumArray(int[] nums) {
        sums = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++)
            sums[i + 1] = sums[i] + nums[i];
    }

    public int sumRange(int i, int j) {
        return sums[j + 1] - sums[i];
    }

}
*/