/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    448. 找到所有数组中消失的数字
        给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。
        请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，
        并以数组的形式返回结果。
    示例 1：
        输入：nums = {4, 3, 2, 7, 8, 2, 3, 1}
        输出：[5,6]
    示例 2：
        输入：nums = {1, 1}
        输出：[2]
*/
public class NO448_E_FindAllNumbersDisappearedInAnArray_x2 {

    @Test
    public void test() {
        assertEquals(getArray(5, 6), findDisappearedNumbers(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));// [5, 6]
        assertEquals(getArray(2), findDisappearedNumbers(new int[]{1, 1}));// [2]
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        return ret;
    }

}

















/**
// 方法1：
public List<Integer> findDisappearedNumbers(int[] nums) {
    // 凡是出现过的数字，考虑到都是正数。
    // 出现过的数字把他们转化成下标并将下标对应的内容最高位置1
    // 在遍历一次数组，那么没出现过的数字的对应得下标则为正数，其他为负数
    // （或者也可以直接判断最高位是不是1）
    List<Integer> res = new ArrayList<>();

    for (int i = 0; i < nums.length; i++)
        nums[(nums[i] & 0x7fffffff) - 1] |= 0x80000000;

    for (int i = 0; i < nums.length; i++)
        if (nums[i] > 0)
            res.add(i+1);

    return res;
}

// 方法2：
public List<Integer> findDisappearedNumbers(int[] nums) {
    List<Integer> ret = new ArrayList<>();
    int n = nums.length;
    for (int num : nums) {
        // 对n取模，还原出本来的值
        int x = (num - 1) % n;
        // 只是为了让他比较突出而已，也可以取反，也可以是大于n的数字
        nums[x] += n;
    }
    for (int i = 0; i < n; i++)
        if (nums[i] <= n)
            ret.add(i + 1);

    return ret;
}
*/