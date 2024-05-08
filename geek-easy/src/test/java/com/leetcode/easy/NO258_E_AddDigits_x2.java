/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    [NUMBER]
    (简单)
    258. 各位相加
        给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。返回这个结果。
    示例 1:
        输入: num = 38
        输出: 2
        解释: 各位相加的过程为：
        38 --> 3 + 8 --> 11
        11 --> 1 + 1 --> 2
        由于 2 是一位数，所以返回 2。
    示例 1:
        输入: num = 0
        输出: 0
    提示：
        0 <= num <= 231 - 1
    方法一：
        思路和算法
        最直观的方法是模拟各位相加的过程，直到剩下的数字是一位数。
        计算一个整数的各位相加的做法是，每次计算当前整数除以 10 的余数得到最低位数，
        将最低位数加到总和中，然后将当前整数除以 10。重复上述操作直到当前整数变成 0，
        此时的总和即为原整数各位相加的结果。
    方法二：
        根据上述分析可知，num > 0 时，其数根的结果在范围 [1, 9]内，
        因此可以想到计算【num − 1 除以 9 的余数】然后加 1。
        由于当 num > 0 时， num − 1 ≥ 0，非负数除以 9 的余数一定也是非负数，
        因此计算 num − 1 除以 9 的余数然后加 1 的结果是正确的。
        当 num − 1 = −1 < 0，负数对 9 取余或取模的结果的正负在不同语言中有所不同。
        对于取余的语言，结果的正负和左操作数相同，则 num−1 对 9 取余的结果为 −1，
        加 1 后得到结果 0，可以得到正确的结果；  `
        对于取模的语言，结果的正负和右操作数相同，则 num−1 对 9 取模的结果为 8，
        加 1 后得到结果 9，无法得到正确的结果，此时需要对 num=0 的情况专门做处理。
*/
@SuppressWarnings("all")
public class NO258_E_AddDigits_x2 {

    @Test
    public void test() {
        assert 2 == addDigits(38);
        assert 0 == addDigits(0);
        info(addDigits(819));
    }

    public int addDigits(int num) {
        return (num - 1) % 9 + 1;
    }

}















/*
public int addDigits(int num) {
    while (num >= 10) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        num = sum;
    }
    return num;
}
*/
