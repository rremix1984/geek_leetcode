/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Character.isDigit;

/**
    [ARRAY] |
    [STRING]
    (简单)
    1796. 字符串中第二大的数字
        给你一个混合字符串s，请你返回s中第二大的数字，如果不存在第二大的数字，
        请你返回-1。
        混合字符串由【小写英文字母】和【数字】组成。
    示例 1：
        输入：s = "dfa12321afd"
        输出：2
        解释：出现在 s 中的数字包括 [1, 2, 3] 。第二大的数字是 2 。
    示例 2：
        输入：s = "abc1111"
        输出：-1
        解释：出现在 s 中的数字只包含 [1] 。没有第二大的数字。
*/
public class NO1796_E_SecondHighest {

    @Test
    public void test() {
        assert 2 == secondHighest("dfa12321afd");
        assert -1 == secondHighest("abc1111");
    }

    public int secondHighest(String s) {
        // 2024/3/12 NO.1 典型题
        int n_2 = -1;
        return n_2;
    }

}
















/*
// 方法1：
public int secondHighest(String s) {
    int n_1 = -1;
    int n_2 = -1;
    for (char ch : s.toCharArray()) {
        int tmp = ch - '0';
        if ('0' > ch || ch > '9')
            continue;

        if (tmp > n_1) {
            n_2 = n_1;
            n_1 = tmp;
        // ch-'0' < n_1 是必须的，防止和 n_1 相等的元素
        } else if (tmp < n_1 && tmp > n_2) {
            n_2 = tmp;
        }
    }
    return n_2;
}
*/