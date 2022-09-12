/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    441. 排列硬币
        你总共有 n 枚硬币，并计划将它们按阶梯状排列。对于一个由 k 行组成的阶梯，
        其第 i 行必须正好有 i 枚硬币。阶梯的最后一行 可能 是不完整的。
        给你一个数字 n ，计算并返回可形成 完整阶梯行 的总行数。
    示例 1：
        输入：n = 5
        输出：2
        解释：因为第三行不完整，所以返回 2 。
    示例 2：
        输入：n = 8
        输出：3
        解释：因为第四行不完整，所以返回 3 。
*/
public class NO441_E_ArrangingCoins_x2 {

    @Test
    public void test() {
        assert 2 == arrangeCoins(5);
        assert 3 == arrangeCoins(8);
        assert 4 == arrangeCoins(10);
        assert 140 == arrangeCoins(10000);
    }

    public int arrangeCoins(int n) {
        if (n == 0)
            return 0;

        return -1;
    }

}




















/**
// 方法1: 二分查找法
public int arrangeCoins(int n) {
    int left = 1, right = n;
    while (left < right) {
        int mid = (right - left + 1) / 2 + left;
        if ((mid * (mid + 1)) / 2 <= n) {
            left = mid;
        } else {
            right = mid - 1;
        }
    }
    return left;
}

// 方法2：暴力法
public int arrangeCoins(int n) {
    for (int i = 1; i <= n; i++) {
        n = n - i;
        if (n <= i)
            return i;
    }
    return 0;
}

// 方法3：牛顿迭代法，效率最高
public int arrangeCoins(int n) {
    if (n == 0) {
        return 0;
    }
    return (int)sqrt(n, n);
}

private double sqrt(double x, int n) {
    double res = (x + (2 * n - x) / x) / 2;
    if (res == x) {
        return x;
    } else {
        return sqrt(res, n);
    }
}
*/