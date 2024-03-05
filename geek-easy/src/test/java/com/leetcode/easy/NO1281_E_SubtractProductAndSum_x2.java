/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [NUMBER]
    (简单)
    1281. 整数的各位积和之差
        给你一个整数 n，请你帮忙计算并返回该整数「各位数字之积」与「各位数字之和」的差。
    示例 1：
        输入：n = 234
        输出：15
        解释：各位数之积 = 2 * 3 * 4 = 24
             各位数之和 = 2 + 3 + 4 = 9
             结果 = 24 - 9 = 15
    示例 2：
        输入：n = 4421
        输出：21
        解释：各位数之积 = 4 * 4 * 2 * 1 = 32
             各位数之和 = 4 + 4 + 2 + 1 = 11
             结果 = 32 - 11 = 21
*/
public class NO1281_E_SubtractProductAndSum_x2 {

    @Test
    public void test() {
        assert 15 == subtractProductAndSum(234);
        assert 21 == subtractProductAndSum(4421);
    }

    public int subtractProductAndSum(int n) {
        int add = 0;
        int mul = 1;
        return mul - add;
    }

}















/**
public int subtractProductAndSum(int n) {
    int add = 0;
    int mul = 1;
    while (n > 0) {
        int digit = n % 10;
        add += digit;
        mul *= digit;
        n /= 10;
    }
    return mul - add;
}
*/