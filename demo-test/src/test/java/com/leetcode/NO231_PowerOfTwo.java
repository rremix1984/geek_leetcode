/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.binaryString;

/**
    （简单）
    231. 2 的幂
        给你一个整数 n，请你判断该整数是否是 2 的幂次方。如果是，返回 true ；否则，返回 false 。
        如果存在一个整数 x 使得 n == 2x ，则认为 n 是 2 的幂次方。
    示例 1：
        输入：n = 1
        输出：true
        解释：2^0 = 1
    示例 2：
        输入：n = 16
        输出：true
        解释：2^4 = 16
    示例 3：
        输入：n = 3
        输出：false
    示例 4：
        输入：n = 4
        输出：true
    示例 5：
        输入：n = 5
        输出：false
*/
public class NO231_PowerOfTwo {

    @Test
    public void test() {
        info(isPowerOfTwo(1));//  true
        info(isPowerOfTwo(16));// true
        info(isPowerOfTwo(3));//  false
        info(isPowerOfTwo(4));//  true
        info(isPowerOfTwo(5));//  false
    }

    public boolean isPowerOfTwo(int n) {
        info("n ->" + binaryString(n));
        info("-n ->" + binaryString(-n));
        return n > 0 && (n & -n) == n;
    }
}














/**
    方法一：二进制表示
        思路与算法
        一个数 nn 是 22 的幂，当且仅当 nn 是正整数，并且 nn 的二进制表示中仅包含 11 个 11。
        因此我们可以考虑使用位运算，将 nn 的二进制表示中最低位的那个 11 提取出来，再判断剩余的数值是否为 00 即可。下面介绍两种常见的与「二进制表示中最低位」相关的位运算技巧。
        第一个技巧是
        \texttt{n \& (n - 1)}
        n & (n - 1)
        其中 \texttt{\&}& 表示按位与运算。该位运算技巧可以直接将 nn 二进制表示的最低位 11 移除，它的原理如下：
        假设 nn 的二进制表示为 (a 10\cdots 0)_2(a10⋯0)
        2
        ，其中 aa 表示若干个高位，11 表示最低位的那个 11，0\cdots 00⋯0 表示后面的若干个 00，那么 n-1n−1 的二进制表示为：
        (a 01\cdots1)_2
        (a01⋯1)
        进行按位与运算，高位 aa 不变，在这之后的所有位都会变为 00，这样我们就将最低位的那个 11 移除了。
        因此，如果 nn 是正整数并且 \texttt{n \& (n - 1) = 0}n & (n - 1) = 0，那么 nn 就是 22 的幂。
        第二个技巧是
        \texttt{n \& (-n)}
        其中 -n−n 是 nn 的相反数，是一个负数。该位运算技巧可以直接获取 nn 二进制表示的最低位的 11。
        由于负数是按照补码规则在计算机中存储的，-n−n 的二进制表示为 nn 的二进制表示的每一位取反再加上 11，因此它的原理如下：
        假设 nn 的二进制表示为 (a 10\cdots 0)_2(a10⋯0)

        ，其中 aa 表示若干个高位，11 表示最低位的那个 11，0\cdots 00⋯0 表示后面的若干个 00，那么 -n−n 的二进制表示为：
        (\bar{a} 01\cdots1)_2 + (1)_2 = (\bar{a} 10\cdots0)_2
        进行按位与运算，高位全部变为 00，最低位的 11 以及之后的所有 00 不变，这样我们就获取了 nn 二进制表示的最低位的 11。
        因此，如果 nn 是正整数并且 \texttt{n \& (-n) = n}n & (-n) = n，那么 nn 就是 22 的幂。

public boolean isPowerOfTwo(int n) {
    return n > 0 && (n & -n) == n;
}
*/