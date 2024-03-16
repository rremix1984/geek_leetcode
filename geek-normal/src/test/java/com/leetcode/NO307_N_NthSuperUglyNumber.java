package com.leetcode;

import org.junit.Test;
import java.util.Arrays;
import static com.leetcode.util.MathUtils.MAX;
import static java.lang.Math.min;

/**
    [ARRAY]
    (中等)
    NO.313 超级丑数
    超级丑数是一个正整数，并满足其所有质因数都出现在质数数组primes中。
    给你一个整数n和一个整数数组primes，返回第n个超级丑数 。
    题目数据保证第n个超级丑数在 32-bit 带符号整数范围内。
    示例 1：
        输入：n = 12, primes = [2, 7, 13, 19]
        输出：32
        解释：给定长度为 4 的质数数组 primes = [2, 7, 13, 19]，
             前 12 个超级丑数序列为：[1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] 。
    示例 2：
        输入：n = 1, primes = [2, 3, 5]
        输出：1
        解释：1 不含质因数，因此它的所有质因数都在质数数组 primes = [2,3,5] 中。
    提示：
        1 <= n <= 105
        1 <= primes.length <= 100
        2 <= primes[i] <= 1000
        题目数据 保证 primes[i] 是一个质数
        primes 中的所有值都 互不相同 ，且按 递增顺序 排列
    Related Topics:数组,数学,动态规划
*/
public class NO307_N_NthSuperUglyNumber {

    @Test
    public void test() {
        assert 32 == nthSuperUglyNumber(12, new int[]{2, 7, 13, 19});
        assert 1 == nthSuperUglyNumber(1, new int[]{2, 3, 5});
        assert 16125 == nthSuperUglyNumber(10000, new int[]{2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269,271,277,281,283,293,307,311,313,317,331,337,347,349,353,359,367,373,379,383,389,397,401,409,419,421,431,433,439,443,449,457,461,463,467,479,487,491,499,503,509,521,523,541});
    }

    public int nthSuperUglyNumber(int n, int[] primes) {
        // 2024/3/16 NO.1
        return -1;
    }

}















/*
// 方法1：动态规划
public int nthSuperUglyNumber(int n, int[] primes) {
    // 2024/3/16 NO.1
    long[] dp = new long[n + 1];
    dp[1] = 1;
    int[] pointers = new int[primes.length];
    Arrays.fill(pointers, 1);
    for (int i = 2; i <= n; i++) {
        long ugly = MAX;
        for (int j = 0; j < primes.length; j++)
            ugly = min(ugly, dp[pointers[j]] * primes[j]);

        dp[i] = ugly;
        for (int j = 0; j < primes.length; j++)
            if (ugly == dp[pointers[j]] * primes[j])
                pointers[j]++;

    }
    return (int) dp[n];
}
*/