/**
 * copyright 2022/1/19
 */
package com.lcp.soeasy;

import org.junit.Test;

/**
    [ARRAY] ||
    (简单)
    (太简单，没意义)
    LCP 06. 拿硬币
        桌上有n堆力扣币，每堆的数量保存在数组coins中。
        我们每次可以选择任意一堆，拿走其中的一枚或者两枚，
        求拿完所有力扣币的最少次数。
    示例 1：
        输入：[4, 2, 1]
        输出：4
        解释：第一堆力扣币最少需要拿 2 次，
             第二堆最少需要拿 1 次，
             第三堆最少需要拿 1 次，总共 4 次即可拿完。
    示例 2：
        输入：[2, 3, 10]
        输出：8
        限制：1 <= n <= 4
             1 <= coins[i] <= 10
    题解
        题目中虽然给了 n 堆硬币，但是最终每一堆都是要拿完的。
        而每一堆拿的情况又不影响其他硬币堆，因此每一堆硬币的拿法实际上是互相独立的。
        于是我们可以只考虑一堆的情况。假设一堆有 x 枚硬币，既然我们的目的是尽早拿
        完所有硬币堆，那么两枚两枚的拿显然是更快的。
        求单堆硬币最小次数：(x+1) // 2
        那么，拿完所有硬币堆只需要循环对所有硬币堆都计算一次，然后求和就可以了。
*/
public class LCP_06_E_MinCount {

    @Test
    public void test() {
        assert 4 == minCount(new int[]{4, 2, 1});
        assert 8 == minCount(new int[]{2, 3, 10});
    }

    public int minCount(int[] coins) {
        // 2024/3/4 NO.1
        // 2024/3/8 NO.2
        int ret = 0;
        return ret;
    }

}
















/*
// 方法1：
public int minCount(int[] coins) {
    int ret = 0;
    for (int coin : coins) {
        // (coin >> 1) + (coin & 1);
        ret += (coin + 1) / 2;
    }
    return ret;
}
*/