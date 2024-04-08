/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING] |||||||||
    （简单）
    NO.680. 验证回文串 II
        给你一个字符串s，最多可以从中删除一个字符。
        请你判断s是否能成为回文字符串：
        如果能，返回true；否则，返回false。
    示例 1：
        输入：s="aba"
        输出：true
    示例 2：
        输入：s="abca"
        输出：true
        解释：你可以删除字符 'c'
    示例 3：
        输入：s="abc"
        输出：false
*/
public class NO680_E_ValidPalindromeII {

    @Test
    public void test() {
        assert  validPalindrome("aba"); // true
        assert  validPalindrome("abca");// true
        assert !validPalindrome("abc"); // false
    }

    public boolean validPalindrome(String s) {
        // 2024/3/4  NO.1
        // 2024/3/8  NO.2
        // 2024/3/11 NO.3
        // 2024/3/17 NO.4 没做出来，很经典的题型
        // 2024/3/18 NO.5 又忘了
        // 2024/3/22-23 NO.6-7 还是不会做
        // 2024/3/26 NO.8 差不多做出来了，但是还是有瑕疵
        // 2024/3/31 NO.9 没思路啊
        int del = 0;
        return dfs(s.toCharArray(), 0, s.length() - 1, del);
    }

    private boolean dfs(char[] chars, int left, int right, int del) {
        while (left < right) {
            if (chars[left] == chars[right]) {
                left++;
                right--;
            } else {
                if (del++ > 0) {
                    return false;
                }
                return dfs(chars, left + 1, right, del)
                    || dfs(chars, left, right - 1, del);
            }
        }
        return true;
    }

}




















/*
// 方法1：剪枝法
public boolean validPalindrome(String s) {
    //可以【删除】或者【不删除】字符
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
            if (++del > 1)
                return false;

            // 递归：左边删一个、右边删一个看哪个成功了
            return validPalindrome(s,i + 1, j, del)
                || validPalindrome(s, i, j - 1, del);
        }
    }
    return true;
}
*/