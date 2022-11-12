/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static java.lang.Math.max;

/**
    (中等)
    343. 整数拆分
        给定一个正整数n，将其拆分为k个正整数的和（k>=2），
        并使这些整数的乘积最大化。
        返回 你可以获得的最大乘积 。
    示例 1:
        输入: n = 2
        输出: 1
        解释: 2 = 1 + 1, 1 × 1 = 1。
    示例 2:
        输入: n = 10
        输出: 36
        解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
    提示:
        2 <= n <= 58

    方法一：动态规划
        对于正整数 n，当 n ≥ 2 时，可以拆分成至少两个正整数的和。令 x 是拆分出的第一个正整数，
    则剩下的部分是 n−x，n−x 可以不继续拆分，或者继续拆分成至少两个正整数的和。
    由于每个正整数对应的最大乘积取决于比它小的正整数对应的最大乘积，因此可以使用动态规划求解。
    创建数组 dp，其中 dp[i] 表示将正整数 i 拆分成至少两个正整数的和之后，
    这些正整数的最大乘积。特别地，0不是正整数，1是最小的正整数，0和1都不能拆分，
    因此 dp[0] = dp[1] = 0。
    当 i ≥ 2 时，假设对正整数 i 拆分出的第一个正整数是 j（1 ≤ j < i），则有以下两种方案：
        将 i 拆分成 j 和 i − j 的和，且 i − j 不再拆分成多个正整数，此时的乘积是 j × (i − j)；
        将 i 拆分成 j 和 i − j 的和，且 i − j 继续拆分成多个正整数，此时的乘积是 j × dp[i − j]。
*/
public class NO343_N_IntegerBreak {

    @Test
    public void test() {
        assert 1 == integerBreak(2);
        assert 36 == integerBreak(10);
    }

    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            int max = 0;
            for (int j = 1; j < i; j++) {
                int cur = max(i - j, dp[i - j]) * j;
                max = max(max, cur);
            }
            dp[i] = max;
        }
        return dp[n];
    }

}















/**
// 方法1：
public int integerBreak(int n) {
    int[] dp = new int[n + 1];
    for (int i = 2; i <= n; i++) {
        int max = 0;
        for (int j = 1; j < i; j++) {
            int cur = max(i - j, dp[i - j]) * j;
            max = max(max, cur);
        }
        dp[i] = max;
    }
    return dp[n];
}
*/