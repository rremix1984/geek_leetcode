/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
 * NO.7
 * 难度【简单】
 *
 */
public class NO70_ClimbStairs {

    @Test
    public void test() {
        info(climbStairs(3));// 3
    }

    public int climbStairs(int n) {
        if (n <= 3)
            return n;
        int n_1 = 2;
        int n_2 = 1;
        for (int i = 3; i <= n; i++) {
            int tmp = n_1;
            n_1 = n_2 + n_1;
            n_2 = tmp;
        }
        return n_1;
    }
}








/**
public int climbStairs(int n) {
    if (n==1)
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