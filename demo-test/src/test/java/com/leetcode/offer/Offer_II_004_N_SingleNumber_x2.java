/**
 * copyright 2022/1/19
 */
package com.leetcode.offer;

import org.junit.Test;

/**
    (中等)
    剑指 Offer II 004. 只出现一次的数字
        给你一个整数数组 nums ，除某个元素仅出现一次外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
    示例 1：
        输入：nums = [2, 2, 3, 2]
        输出：3
    示例 2：
        输入：nums = [0, 1, 0, 1, 0, 1, 100]
        输出：100
*/
public class Offer_II_004_N_SingleNumber_x2 {

   @Test
   public void test() {
       assert 3 == singleNumber(new int[]{2, 2, 3, 2});
       assert 100 == singleNumber(new int[]{0, 1, 0, 1, 0, 1, 100});
       assert 500 == singleNumber(new int[]{30000, 500, 100, 30000, 100, 30000, 100});
   }

    public int singleNumber(int[] nums) {
        int res = 0;
        return res;
    }

}

















/**
// 方法1:
public int singleNumber(int[] nums) {
    int res = 0;

    // 遍历32位int二进制表示下的每一位（从低到高）
    for (int i = 0; i < 32; i++) {
        int count = 0;

        // 计算所有数当前位1的个数总和
        for (int num : nums)
            // 左移将第i位移到最低位，&1判断该位是否为1
            count += (num >> i) & 1;

        // 如果该位1个数不是三的倍数，说明该位包含了独立数的对应位
        if (count % 3 != 0)
            res |= 1<<i;
    }
    return res;
}
*/