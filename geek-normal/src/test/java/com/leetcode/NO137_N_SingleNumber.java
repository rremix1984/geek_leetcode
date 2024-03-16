package com.leetcode;

import org.junit.Test;

/**
    [ARRAY]
    (中等)
    NO.137 只出现一次的数字 II
    给你一个整数数组nums，除某个元素仅出现一次外，其余每个元素都恰出现三次。
    请你找出并返回那个只出现了一次的元素。
    你必须设计并实现线性时间复杂度的算法且使用常数级空间来解决此问题。
    示例 1：
        输入：nums = [2,2,3,2]
        输出：3
    示例 2：
        输入：nums = [0,1,0,1,0,1,99]
        输出：99
    提示：
        1 <= nums.length <= 3 * 104
        -231 <= nums[i] <= 231 - 1
        nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次
    Related Topics:位运算,数组
*/
public class NO137_N_SingleNumber {

    @Test
    public void test() {
        assert 3 == singleNumber(new int[]{2, 2, 3, 2});
        assert 99 == singleNumber(new int[]{0, 1, 0, 1, 0, 1, 99});
    }

    public int singleNumber(int[] nums) {
         return -1;
    }

}














/*
// 方法1：
public int singleNumber(int[] nums) {
    int a = 0;      // 二进制记录每个位出现1的次数的高位
    int b = 0;      // 二进制记录每个位出现1的次数的低位
    for(int num: nums){
        // 暂存原来的a
        int tmpA = a;

        // a和b同时为0，a强制为0；
        // 否则a的每一位根据num的每一位状态转移
        a = (a | b) & (a ^ num);

        // a为1时，b强制为0；
        // 否则b的每一位根据num的每一位状态转移
        b = (~tmpA) & (b ^ num);
    }
    return b;   // 最终结果就存储在b中
}
*/