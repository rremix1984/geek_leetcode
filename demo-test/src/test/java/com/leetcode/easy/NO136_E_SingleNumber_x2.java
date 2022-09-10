/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    136. 只出现一次的数字
        给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。
        找出那个只出现了一次的元素。
        说明：你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
    示例 1:
        输入: {2, 2, 1}
        输出: 1
    示例 2:
        输入: {4, 1, 2, 1, 2}
        输出: 4
*/
public class NO136_E_SingleNumber_x2 {

    @Test
    public void test() {
        assertEquals(1, singleNumber(new int[]{2, 2, 1}));
        assertEquals(4, singleNumber(new int[]{4, 1, 2, 1, 2}));
        assertEquals(2, singleNumber(new int[]{4, 1, 2, 1, 4}));
    }

    private int singleNumber(int[] nums) {
        int ans = 0;
        for (int n : nums) {
            ans ^= n;
        }
        return ans;
    }

}
















/**
 答案是使用位运算。对于这道题，可使用异或运算 ⊕。异或运算有以下三个性质
    1）任何数和 00 做异或运算，结果仍然是原来的数，即 a ⊕ 0 = a
    2）任何数和其自身做异或运算，结果是 0，即 a ⊕ a = 0
    3）异或运算满足交换律和结合律，即  a ⊕ b ⊕ a
                                = b ⊕ a ⊕ a
                                = b ⊕ (a ⊕ a)
                                = b ⊕ 0
                                = b
// 方法1：
private int singleNumber(int[] nums) {
    int ans = 0;
    for (int n : nums)
        ans ^= n;

    return ans;
}
*/