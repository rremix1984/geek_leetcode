/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    680. 验证回文串 II
        给你一个字符串 s，最多可以从中删除一个字符。
        请你判断 s 是否能成为回文字符串：如果能，返回 true ；否则，返回 false 。
    示例 1：
        输入：s = "aba"
        输出：true
    示例 2：
        输入：s = "abca"
        输出：true
        解释：你可以删除字符 'c'
    示例 3：
        输入：s = "abc"
        输出：false
*/
public class NO680_E_ValidPalindromeII_x2 {

    @Test
    public void test() {
        assert validPalindrome("aba");// true
        assert validPalindrome("abca");// true
        assert !validPalindrome("abc");// false
    }

    public boolean validPalindrome(String s) {
        return true;
    }

}




















/**
// 递归方法：
public boolean validPalindrome(String s) {
    //可以 删除 或者 不删除字符
    return validPalindrome(s, 0, s.length() - 1, 0);
}

public boolean validPalindrome(String s, int i, int j, int del) {
    //可以删除或者不删除字符
    while (i < j) {
        // 两头相等，就像中间凑
        if (s.charAt(i) == s.charAt(j)) {
            i++;
            j--;
        // 两头不相等了
        } else {
            // 如果已经删除过一次字符，就失败了
            if (del >= 1)
                return false;

            // 没删除过，先删除一个（del++）试试
            del++;

            // 递归：左边删一个、右边删一个看哪个成功了
            return validPalindrome(s,i + 1, j, del)
                    || validPalindrome(s, i, j - 1, del);
        }
    }
    return true;
}
*/