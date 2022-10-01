/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2235. 两整数相加
        给你两个整数 num1 和 num2，返回这两个整数的和。
    示例 1：
        输入：num1 = 12, num2 = 5
        输出：17
        解释：num1 是 12，num2 是 5 ，它们的和是 12 + 5 = 17 ，因此返回 17 。
    示例 2：
        输入：num1 = -10, num2 = 4
        输出：-6
        解释：num1 + num2 = -6 ，因此返回 -6 。
*/
public class NO2235_E_Sum_x2 {

    @Test
    public void test() {
        assert 17 == sum(12, 5);
        assert -6 == sum(-10, 4);
    }

    public int sum(int num1, int num2) {
        return -1;
    }

}















/**
// 方法1：
public int sum(int num1, int num2) {
    return num1 + num2;
}
*/