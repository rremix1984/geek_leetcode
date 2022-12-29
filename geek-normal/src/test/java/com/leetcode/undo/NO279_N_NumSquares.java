/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import static java.lang.Math.min;

/**
    (中等)
    279. 完全平方数
        给你一个整数n，返回和为n的完全平方数的最少数量 。
        完全平方数是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。
        例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
    示例 1：
        输入：n = 12
        输出：3
        解释：12 = 4 + 4 + 4
    示例 2：
        输入：n = 13
        输出：2
        解释：13 = 4 + 9
    提示：
        1 <= n <= 10 ^ 4

    方法一：动态规划
        我们可以依据题目的要求写出状态表达式：f[i] 表示最少需要多少个数的平方来表示整数 i。
    这些数必然落在区间 [1, \sqrt{n}]。我们可以枚举这些数，假设当前枚举到 j，那么我们还需要取若干数的平方，构成 i - j ^ 2。
    此时我们发现该子问题和原问题类似，只是规模变小了。这符合了动态规划的要求，于是我们可以写出状态转移方程。
        f[i] = 1 + \min_{j = 1} ^ {\lfloor\sqrt{i}\rfloor} {f[i - j ^ 2]}
    其中 f[0] = 0 为边界条件，实际上我们无法表示数字 0，只是为了保证状态转移过程中遇到 j 恰为 sqrt{i} 的情况合法。
    同时因为计算 f[i] 时所需要用到的状态仅有 f[i - j ^ 2]，必然小于 i，因此我们只需要从小到大地枚举 i 来计算 f[i] 即可。
*/
public class NO279_N_NumSquares {

    @Test
    public void test() {
        assert 3 == numSquares(12);
        assert 2 == numSquares(13);
        assert 3 == numSquares(11);
    }

    public int numSquares(int n) {
        return -1;
    }

}
















/**
// 方法1：
public int numSquares(int n) {
    int[] f = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        int min = Integer.MAX_VALUE;
        for (int j = 1; j * j <= i; j++)
            min = min(min, f[i - j * j]);

        f[i] = min + 1;
    }
    return f[n];
}

// 方法2：
public int numSquares(int n) {
    if (isPerfectSquare(n))
        return 1;

    if (checkAnswer4(n))
        return 4;

    for (int i = 1; i * i <= n; i++) {
        if (isPerfectSquare(n - i * i))
            return 2;
    }
    return 3;
}

// 判断是否为完全平方数
public boolean isPerfectSquare(int x) {
    int y = (int) Math.sqrt(x);
    return y * y == x;
}

// 判断是否能表示为 4^k*(8m+7)
public boolean checkAnswer4(int x) {
    while (x % 4 == 0)
        x /= 4;

    return x % 8 == 7;
}
*/