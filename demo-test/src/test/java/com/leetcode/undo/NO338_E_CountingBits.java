/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    338. 比特位计数
        给你一个整数 n ，对于 0 <= i <= n 中的每个 i，
        计算其二进制表示中 1 的个数，返回一个长度为 n + 1 的数组 ans 作为答案。
    示例 1：
        输入：n = 2
        输出：{0, 1, 1}
        解释：0 --> 0
             1 --> 1
             2 --> 10
    示例 2：
        输入：n = 5
        输出：{0, 1, 1, 2, 1, 2}
        解释：0 --> 0
             1 --> 1
             2 --> 10
             3 --> 11
             4 --> 100
             5 --> 101

    方法二：动态规划——最高有效位
        方法一需要对每个整数使用 O(logn) 的时间计算「一比特数」。可以换一个思路，当计算 i 的「一比特数」时，如果存在 0≤j<i，
        j 的「一比特数」已知，且 i 和 j 相比，i 的二进制表示只多了一个 1，则可以快速得到 i 的「一比特数」。
        令 bits[i] 表示 i 的「一比特数」，则上述关系可以表示成：bits[i] = bits[j]+1。
        对于正整数 xx，如果可以知道最大的正整数 yy，使得 y≤x 且 y 是 2 的整数次幂，则 y 的二进制表示中只有最高位是 1，
        其余都是 0，此时称 y 为 x 的「最高有效位」。令 z=x−y，显然 0≤z<x，则 bits[x]=bits[z]+1。
        为了判断一个正整数是不是 2 的整数次幂，可以利用方法一中提到的按位与运算的性质。如果正整数 y 是 2 的整数次幂，
        则 y 的二进制表示中只有最高位是 1，其余都是 0，因此 y&(y−1)=0。由此可见，正整数 y 是 2 的整数次幂，
        当且仅当 y&(y−1)=0。
        显然，0 的「一比特数」为 0。使用 highBit 表示当前的最高有效位，遍历从 1 到 n 的每个正整数 i，进行如下操作。
        如果 i&(i−1)=0，则令 highBit=i，更新当前的最高有效位。
        i 比 i−highBit 的「一比特数」多 1，由于是从小到大遍历每个整数，因此遍历到 i 时，
        i−highBit 的「一比特数」已知，令 bits[i]=bits[i−highBit]+1。
        最终得到的数组 bits 即为答案。
*/
public class NO338_E_CountingBits {

    @Test
    public void test() {
        info(countBits(2));// [0, 1, 1]
        info(countBits(5));// [0, 1, 1, 2, 1, 2]
    }

    public int[] countBits(int n) {
        int[] dp = new int[n + 1];

        // dp动态规划
        for (int i = 1; i <= n; i++)
            dp[i] = dp[i & (i - 1)] + 1;

        return dp;
    }
}

















/**
// 方法1：
public int[] countBits(int n) {
    int[] bits = new int[n + 1];
    int highBit = 0;
    for (int i = 1; i <= n; i++) {
        if ((i & (i - 1)) == 0)
            highBit = i;
        bits[i] = bits[i - highBit] + 1;
    }
    return bits;
}
*/