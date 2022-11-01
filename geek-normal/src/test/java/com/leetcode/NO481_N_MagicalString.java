/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (中等)
    481. 神奇字符串
        神奇字符串s仅由'1'和'2'组成，并需要遵守下面的规则：
          1）神奇字符串s的神奇之处在于，串联字符串中'1'和'2'的连续出现次数可以生成该字符串。
        s的前几个元素是s="1221121221221121122……"。如果将s中连续的若干1和2进行分组，
        可以得到"1 22 11 2 1 22 1 22 11 2 11 22 ......"。每组中1或者2的出现次数
        分别是"1 2 2 1 1 2 1 2 2 1 2 2 ......"。上面的出现次数正是s自身。
        给你一个整数n，返回在神奇字符串s的前n个数字中1的数目。
    示例 1：
        输入：n = 6
        输出：3
        解释：神奇字符串 s 的前 6 个元素是 “122112”，它包含三个 1，因此返回 3 。
    示例 2：
        输入：n = 1
        输出：1
    提示：
        1 <= n <= 10 ^ 5
*/
public class NO481_N_MagicalString {

    @Test
    public void test() {
        assert 3 == magicalString(6);
        assert 1 == magicalString(1);
    }

    public int magicalString(int n) {
        StringBuilder sb = new StringBuilder("122");
        int num = 1;
        for (int i = 2; i < sb.length() && sb.length() <= n; i++) {
            // 当前为1，sb要拼接1个字符
            if (sb.charAt(i) == '1')
                sb.append(num);
            else
                // 当前为2，sb要拼接2个字符
                sb.append(num).append(num);
            // 1 -> 2, 2 -> 1
            num ^= 3;
        }

        int ans = 0;
        for (int i = 0; i < n; i++)
            if (sb.charAt(i) == '1')
                ans++;

        return ans;
    }

}



















/**
// 方法1：
public int magicalString(int n) {
    if (n < 4)
        return 1;

    char[] s = new char[n];
    s[0] = '1';
    s[1] = '2';
    s[2] = '2';
    int res = 1;
    int i = 2;
    int j = 3;
    while (j < n) {
        int size = s[i] - '0';
        int num = 3 - (s[j - 1] - '0');
        while (size > 0 && j < n) {
            s[j] = (char) ('0' + num);
            if (num == 1) {
                ++res;
            }
            ++j;
            --size;
        }
        ++i;
    }
    return res;
}
*/