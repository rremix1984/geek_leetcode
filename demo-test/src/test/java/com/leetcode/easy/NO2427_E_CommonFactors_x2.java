/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2427. 公因子的数目
        给你两个正整数 a 和 b ，返回 a 和 b 的 公 因子的数目。
        如果 x 可以同时整除 a 和 b ，则认为 x 是 a 和 b 的一个 公因子 。
    示例 1：
        输入：a = 12, b = 6
        输出：4
        解释：12 和 6 的公因子是 1、2、3、6 。
    示例 2：
        输入：a = 25, b = 30
        输出：2
        解释：25 和 30 的公因子是 1、5 。
*/
public class NO2427_E_CommonFactors_x2 {

    @Test
    public void test() {
        assert 4 == commonFactors(12, 6);
        assert 2 == commonFactors(25, 30);
        assert 8 == commonFactors(885, 885);
    }

    public int commonFactors(int a, int b) {
        int count = 0;
        return count;
    }

}

















/**
// 方法1：
public int commonFactors(int a, int b) {
    int count = 0;
    // 枚举
    for (int i = 1; i <= a && i <= b; i++)
        if (a % i == 0 && b % i == 0)
            // 统计
            count++;

    return count;
}
*/