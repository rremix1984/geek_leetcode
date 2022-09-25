/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;

/**
    (简单)
    剑指 Offer 65. 不用加减乘除做加法
        写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。
    示例:
        输入: a = 1, b = 1
        输出: 2
*/
public class Offer65_E_Add_x2 {

    @Test
    public void test() {
        assert 2 == add(1, 1);
        assert 198 == add(99, 99);
    }

    public int add(int a, int b) {
        return -1;
    }

}
















/**
// 方法1：
public int add(int a, int b) {
    while (b != 0) {
        // 所有需要进位的位为 a & b，进位后的进位结果为 (a & b) << 1。
        int carry = (a & b) << 1;

        // 不考虑进位情况下，异或（^）相当于加法
        a = a ^ b;

        b = carry;
    }
    return a;
}
*/