/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1790. 仅执行一次字符串交换能否使两个字符串相等
        给你长度相等的两个字符串 s1 和 s2 。一次 字符串交换 操作的步骤如下：
        选出某个字符串中的两个下标（不必不同），并交换这两个下标所对应的字符。
        如果对 其中一个字符串 执行 最多一次字符串交换 就可以使两个字符串相等，
        返回 true ；否则，返回 false 。
    示例 1：
        输入：s1 = "bank", s2 = "kanb"
        输出：true
        解释：例如，交换 s2 中的第一个和最后一个字符可以得到 "bank"
    示例 2：
        输入：s1 = "attack", s2 = "defend"
        输出：false
        解释：一次字符串交换无法使两个字符串相等
    示例 3：
        输入：s1 = "kelb", s2 = "kelb"
        输出：true
        解释：两个字符串已经相等，所以不需要进行字符串交换
    示例 4：
        输入：s1 = "abcd", s2 = "dcba"
        输出：false
*/
public class NO1790_E_AreAlmostEqual_x2 {

    @Test
    public void test() {
        assert areAlmostEqual("bank", "kanb");
        assert !areAlmostEqual("attack", "defend");
        assert areAlmostEqual("kelb", "kelb");
        assert !areAlmostEqual("abcd", "dcba");
    }

    public boolean areAlmostEqual(String s1, String s2) {
        boolean res = false;
        return res;
    }

}


















/**
public boolean areAlmostEqual(String s1, String s2) {
    if (s1.equals(s2))
        return true;

    char a = ' ';
    char b = ' ';
    boolean res = false;

    for (int i = 0; i < s1.length(); i++) {
        // 相等的前提下，直接看下一个字符
        if (s1.charAt(i) == s2.charAt(i))
            continue;

        // 如果两个字符串不相等，先把 a,b 记录下来
        if (a == ' ') {
            a = s1.charAt(i);
            b = s2.charAt(i);
            continue;
        }

        // 上一次不相等了，切 a -> b 互换位置之后也相等
        if (a == s2.charAt(i) && b == s1.charAt(i) && !res)
            res = true;
        else
            return false;
    }

    return res;
}
*/