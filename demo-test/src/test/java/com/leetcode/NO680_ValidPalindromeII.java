/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    680. 验证回文串 II
        给你一个字符串 s，最多 可以从中删除一个字符。
        请你判断 s 是否能成为回文字符串：如果能，返回 true ；否则，返回 false 。
    示例 1：
        输入：s = "aba"
        输出：true
    示例 2：
        输入：s = "abca"
        输出：true
        解释：你可以删除字符 'c' 。
    示例 3：
        输入：s = "abc"
        输出：false
*/
public class NO680_ValidPalindromeII {

    @Test
    public void test() {
        info(validPalindrome("aba"));// true
        info(validPalindrome("abca"));// true
        info(validPalindrome("abc"));// false
    }

    public boolean validPalindrome(String s) {
        //可以删除或者不删除字符
        return validPalindrome(s, 0, s.length() - 1, 0);
    }

    public boolean validPalindrome(String s, int i, int j, int del) {
        //可以删除或者不删除字符
        while(i < j){
            if(s.charAt(i) == s.charAt(j)){
                i++;
                j--;
            }
            else{
                if(del >= 1){
                    return false;
                }
                del++;
                return validPalindrome(s, i + 1, j, del) || validPalindrome(s, i, j - 1, del);
            }
        }
        return true;
    }
}