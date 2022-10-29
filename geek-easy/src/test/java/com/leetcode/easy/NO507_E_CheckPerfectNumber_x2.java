/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    507. 完美数
        对于一个 正整数，如果它和除了它自身以外的所有 正因子 之和相等，我们称它为 「完美数」。
        给定一个 整数 n， 如果是完美数，返回 true；否则返回 false。
    示例 1：
        输入：num = 28
        输出：true
        解释：28 = 1 + 2 + 4 + 7 + 14
        1, 2, 4, 7, 和 14 是 28 的所有正因子。
    示例 2：
        输入：num = 7
        输出：false
    提示：
        1 <= num <= 108

    方法一：枚举
        我们可以枚举 \textit{num}num 的所有真因子，累加所有真因子之和，记作 \textit{sum}sum。若 \textit{sum}=\textit{num}sum=num 则返回 \texttt{true}true，否则返回 \texttt{false}false。
    在枚举时，我们只需要枚举不超过 sqrt\num
    的数。这是因为如果 num 有一个大于 sqrt\num的因数 d，那么它一定有一个小于 sqrt\num的因数num/d。
    在枚举时，若找到了一个因数 dd，那么就找到了因数num / d。注意当 d⋅d=num 时这两个因数相同，此时不能重复计算。
*/
public class NO507_E_CheckPerfectNumber_x2 {

    @Test
    public void test() {
        assert checkPerfectNumber(28);
        assert !checkPerfectNumber(7);
    }

    public boolean checkPerfectNumber(int num) {
        return false;
    }

}


















/**
// 方法1：
public boolean checkPerfectNumber(int num) {
    if (num == 1)
        return false;

    int sum = 1;
    for (int d = 2; d * d <= num; d++)
        if (num % d == 0) {
            sum += d;
            if (d * d < num)
                sum += num / d;
        }

    return sum == num;
}
*/