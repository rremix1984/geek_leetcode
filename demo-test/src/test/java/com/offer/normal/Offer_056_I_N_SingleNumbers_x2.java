/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (中等)
    剑指 Offer 56 - I. 数组中数字出现的次数
        一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。
        请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
    示例 1：
        输入：nums = {4, 1, 4, 6}
        输出：{1, 6} 或 {6, 1}
    示例 2：
        输入：nums = {1, 2, 10, 4, 1, 4, 3, 3}
        输出：{2, 10} 或 {10, 2}
*/
public class Offer_056_I_N_SingleNumbers_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 6}, singleNumbers(new int[]{4, 1, 4, 6}));
        assertArrayEquals(new int[]{10, 2}, singleNumbers(new int[]{1, 2, 10, 4, 1, 4, 3, 3}));
    }

    public int[] singleNumbers(int[] nums) {
        int a = 0;// 分组A, 里面含有 a（只出现一次的数字），某一位 div 为 1
        int b = 0;// 分组B, 里面含有 b（只出现一次的数字），某一位 div 为 0
        return new int[]{a, b};
    }

}
















/**
// 方法1：
public int[] singleNumbers(int[] nums) {
    int ret = 0;
    for (int n : nums)
        ret ^= n;

    int div = 1;
    // 找到第一位不是 0 的
    while ((div & ret) == 0)
        div <<= 1;

    int a = 0;// 分组A, 里面含有 a（只出现一次的数字），某一位 div 为 1
    int b = 0;// 分组B, 里面含有 b（只出现一次的数字），某一位 div 为 0

    for (int n : nums)
        // 根据该为是否为0，分为2组
        if ((div & n) != 0)
            a ^= n;// div 等于 1 的一组
        else
            b ^= n;// div 等于 0 的一组

    return new int[]{a, b};
}
*/