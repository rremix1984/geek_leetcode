/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**
   （简单）
    169. 多数元素
        给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指
        在数组中出现次数 大于 ⌊n / 2⌋ 的元素。你可以假设数组是非空的，
        并且给定的数组总是存在多数元素。
    示例 1：
        输入：nums = [3, 2, 3]
        输出：3
    示例 2：
        输入：nums = [2, 2, 1, 1, 1, 2, 2]
        输出：2
*/
public class NO169_E_MajorityElement_x3 {

    @Test
    public void test() {
        assert  3 == majorityElement(new int[]{3, 2, 3});// 3
        assert -1 == majorityElement(new int[]{3, 2});// -1
        assert  2 == majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2});// 2
        assert  5 == majorityElement(new int[]{1, 2, 5, 9, 5, 9, 5, 5, 5});
        assert  2 == majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2});
    }

    public int majorityElement(int[] nums) {
        int cand_num = nums[0];
        return cand_num;
    }

}















/**
// 方法1：
public int majorityElement(int[] nums) {
    int cand_num = nums[0];
    int count = 1;
    for (int i = 1; i < nums.length; ++i) {
        if (cand_num == nums[i])
            ++count;

        else if (--count == 0) {
            cand_num = nums[i];
            count = 1;
        }
    }
    int c = 0;
    for (int num : nums)
        if (num == cand_num)
            c++;

    if ((nums.length / c) > 1)
        return -1;

    return cand_num;
}
*/