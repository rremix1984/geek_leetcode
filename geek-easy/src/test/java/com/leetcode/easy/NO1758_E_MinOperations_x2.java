/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.min;

/**
    [STRING]
    (简单)
    1758. 生成交替二进制字符串的最少操作数
        给你一个仅由字符 '0' 和 '1' 组成的字符串 s 。一步操作中，你可以将任一 '0' 变成 '1' ，或者将 '1' 变成 '0' 。
        交替字符串 定义为：如果字符串中不存在相邻两个字符相等的情况，那么该字符串就是交替字符串。
        例如，字符串 "010" 是交替字符串，而字符串 "0100" 不是。
        返回使 s 变成 交替字符串 所需的 最少 操作数。
    示例 1：
        输入：s = "0100"
        输出：1
        解释：如果将最后一个字符变为 '1' ，s 就变成 "0101" ，即符合交替字符串定义。
    示例 2：
        输入：s = "10"
        输出：0
        解释：s 已经是交替字符串。
    示例 3：
        输入：s = "1111"
        输出：2
        解释：需要 2 步操作得到 "0101" 或 "1010" 。

    两种情况：
        1.偶数位为0，奇数位为1
            这种情况下，任意位的值和索引奇偶性相同，即s[i] % 2 == i % 2，若不满足，即需要变动该位，则计数cnt1++
        2.偶数位为1，奇数位为0
            这种情况下，任意位的值和索引奇偶性不同，即s[i] % 2 != i % 2，若不满足，即需要变动该位，则计数cnt2++

    比较哪种需要变动的位数小
*/
public class NO1758_E_MinOperations_x2 {

    @Test
    public void test() {
        assert 1 == minOperations("0100");
        assert 0 == minOperations("10");
        assert 2 == minOperations("1111");
    }

    public int minOperations(String s) {
        return 0;
    }

}















/**
// 方法1：
public int minOperations(String s) {
    // 第一位奇偶性（1-奇，0-偶）
    // 第二位奇偶性（1-奇，0-偶）
    int[][] a = new int[2][2];
    for (int i = 0; i < s.length(); i++)
        // 坐标和值的奇偶性，有四种情况
        // [0][0]+[1][1] 或者 [1][0]+[0][1]
        //a[i & 1][s.charAt(i) & 1]++;
        a[i % 2][s.charAt(i) % 2]++;

    // 把这几种可能性都拼在一起，取其中最小的一种即可
    return min(a[0][1] + a[1][0], a[0][0] + a[1][1]);
}

// 如果想组成 01010101 或者 1010101010
// 特点是【坐标（i）】和【值（s.charAt(i)）】的奇偶性全部相同（2种），或者全部不相同（2种）
// 2 x 2 = 4（种）
public int minOperations(String s) {
    int cnt1 = 0;
    int cnt2 = 0;
    for (int i = 0; i < s.length(); i++)
        if (s.charAt(i) % 2 != i % 2)
            cnt1++;
        else
            cnt2++;

    return min(cnt1, cnt2);
}
*/