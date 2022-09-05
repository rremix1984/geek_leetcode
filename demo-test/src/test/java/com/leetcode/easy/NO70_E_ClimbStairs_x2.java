/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.SwapUtil.swap;

/**
    70. 爬楼梯
        假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
        每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
    示例 1：
        输入：n = 2
        输出：2
        解释：有两种方法可以爬到楼顶。
            1. 1 阶 + 1 阶
            2. 2 阶
    示例 2：
        输入：n = 3
        输出：3
        解释：有三种方法可以爬到楼顶。
            1. 1 阶 + 1 阶 + 1 阶
            2. 1 阶 + 2 阶
            3. 2 阶 + 1 阶
*/
public class NO70_E_ClimbStairs_x2 {

    @Test
    public void test() {
        info(climbStairs(2));// 2
        info(climbStairs(3));// 3
        info(climbStairs(4));// 5
        info(climbStairs(10));// 89
    }

    public int climbStairs(int n) {
        if (n < 2)
            return n;
        int n_2 = 1;
        int n_1 = 2;
        for (int i = 3; i <= n; i++) {
            int tmp = n_1;
            n_1 = n_1 + n_2;
            n_2 = tmp;
        }
        return n_1;
    }
}








/**
public int climbStairs(int n) {
    if (n == 1)
        return 1;
    int s2 = 1;
    int s1 = 2;
    for (int i = 3; i <= n; i++) {
        int tmp = s1;
        s1 = s1 + s2;
        s2 = tmp;
    }
    return s1;
}
 */