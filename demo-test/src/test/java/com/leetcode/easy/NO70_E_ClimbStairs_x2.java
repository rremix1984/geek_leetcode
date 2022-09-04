/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
 * NO.7
 * 难度【简单】
 *
 */
public class NO70_E_ClimbStairs_x2 {

    @Test
    public void test() {
        info(climbStairs(4));// 5
        info(climbStairs(10));// 89
    }

    public int climbStairs(int n) {
        return -1;
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