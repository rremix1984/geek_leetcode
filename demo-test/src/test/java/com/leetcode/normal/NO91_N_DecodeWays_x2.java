/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    (中等)
    91. 解码方法
        一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
            'A' -> "1"
            'B' -> "2"
            ... ...
            'Z' -> "26"
        要解码已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母
        （可能有多种方法）。例如，"11106" 可以映射为：
            "AAJF" ，将消息分组为 (1 1 10 6)
            "KJF" ，将消息分组为 (11 10 6)
           注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是
        由于 "6" 和 "06" 在映射中并不等价。给你一个只含数字的 非空 字符串 s，
        请计算并返回【解码】方法的【总数】。题目数据保证答案肯定是一个 32 位 的整数。
    示例 1：
        输入：s = "12"
        输出：2
        解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
    示例 2：
        输入：s = "226"
        输出：3
        解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
    示例 3：
        输入：s = "0"
        输出：0
        解释：没有字符映射到以 0 开头的数字。
             含有 0 的有效映射是 'J' -> "10" 和 'T'-> "20" 。
             由于没有字符，因此没有有效的方法对此进行解码，因为所有数字都需要映射。
*/
public class NO91_N_DecodeWays_x2 {

    @Test
    public void test() {
        assert 2 == numDecodings("12");// 2
        assert 3 == numDecodings("226");// 3
        assert 0 == numDecodings("0");// 0
        assert 3 == numDecodings("123");// 3
    }

    public int numDecodings(String s) {
        return -1;
    }
}
















/**
// 方法1：dp动态规划
public int numDecodings(String s) {
    // 多1位，为了能dp
    int[] dp = new int[s.length() + 1];
    // 默认的第【0】位只有 1 种方案
    dp[0] = 1;
    for (int i = 1; i <= s.length(); i++) {
        if (s.charAt(i - 1) != '0')
            dp[i] += dp[i - 1];

        // （2）特殊情况判断，如果拼在一起还能组成一个 1 < n < 26的数字，
        // 那么就把这种可能性加到 dp[i] 上
        if (i > 1 // 不是第【1】个元素
                // 前2位不是【0】
                && s.charAt(i - 2) != '0'
                // 前2位 * 10 + 前1位 在26个字母范围内（也就是【1】到【26】之间)
                && ((s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0') <= 26))
            dp[i] += dp[i - 2];
    }
    return dp[s.length()];
}
*/