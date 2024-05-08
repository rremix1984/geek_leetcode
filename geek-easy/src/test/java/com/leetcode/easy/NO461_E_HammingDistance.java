/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    461. 汉明距离
        两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
        给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
    示例 1：
        输入：x = 1, y = 4
        输出：2
        解释：1   (0 0 0 1)
             4   (0 1 0 0)
                    ↑   ↑
        上面的箭头指出了对应二进制位不同的位置。
    示例 2：
        输入：x = 3, y = 1
        输出：1
        解释：3   (0 0 1 1)
             1   (0 0 0 1)
                      ↑
*/
@SuppressWarnings("all")
public class NO461_E_HammingDistance {

    @Test
    public void test() {
        assertEquals(2, hammingDistance(1,4));
        assertEquals(1, hammingDistance(3,1));
    }

    public int hammingDistance(int x, int y) {
        int ret = 0;
        return ret;
    }
}
















/*
public int hammingDistance(int x, int y) {
    int s = x ^ y, ret = 0;
    while (s != 0) {
        s &= s - 1;
        ret++;
    }
    return ret;
}

public int hammingDistance(int x, int y) {
    int s = x ^ y, ret = 0;
    while (s != 0) {
        ret += s & 1;
        s >>= 1;
    }
    return ret;
}
*/