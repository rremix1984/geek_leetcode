/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    338. 比特位计数
        给你一个整数 n ，对于 0 <= i <= n 中的每个 i ，计算其二进制表示中 1 的个数 ，
        返回一个长度为 n + 1 的数组 ans 作为答案。
    示例 1：
        输入：n = 2
        输出：[0, 1, 1]
        解释：0 --> 0      2 --> 10
             1 --> 1
    示例 2：
        输入：n = 5
        输出：[0,1,1,2,1,2]
        解释：0 --> 0        3 --> 11
             1 --> 1        4 --> 100
             2 --> 10       5 --> 101
*/
public class NO338_E_CountBits_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{0, 1, 1}, countBits(2));
        assertArrayEquals(new int[]{0, 1, 1, 2, 1, 2}, countBits(5));
    }

    public int[] countBits(int num) {
        int[] ans = new int[num + 1];
        return ans;
    }

}















/**
// 方法1：
public int[] countBits(int n) {
    int[] bits = new int[n + 1];
    for (int i = 0; i <= n; i++)
        bits[i] = countOnes(i);

    return bits;
}

public int countOnes(int x) {
    int ones = 0;
    while (x > 0) {
        x &= (x - 1);
        ones++;
    }
    return ones;
}


// 方法2：
public int[] countBits(int num) {
    int[] result = new int[num + 1];
    for (int i = 1; i <= num; i++)
        // 1、偶数
        if ((i & 1) == 0)
            result[i] = result[i >> 1];
            // 2、奇数
        else
            result[i] = result[i - 1] + 1;
    return result;
}
*/