/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;

/**
    (简单)
    剑指 Offer II 018. 有效的回文
        给定一个字符串 s ，验证 s 是否是 回文串 ，只考虑字母和数字字符，可以忽略字母的大小写。
        本题中，将空字符串定义为有效的 回文串 。
    示例 1:
        输入: s = "A man, a plan, a canal: Panama"
        输出: true
        解释："amanaplanacanalpanama" 是回文串
    示例 2:
        输入: s = "race a car"
        输出: false
        解释："raceacar" 不是回文串
*/
public class OfferII_018_E_IsPalindrome_x2 {

    @Test
    public void test() {
        assert isPalindrome("A man, a plan, a canal: Panama");
        assert !isPalindrome("race a car");
    }

    public boolean isPalindrome(String s) {
        return true;
    }

}




















/**
// 方法1：
public boolean isPalindrome(String s) {
    int i = 0;
    int j = s.length() - 1;
    while (i < j) {
        char a = s.charAt(i);
        char b = s.charAt(j);
        if (!Character.isLetterOrDigit(a)) {
            i++;
            continue;
        }

        if (!Character.isLetterOrDigit(b)) {
            j--;
            continue;
        }

        if (Character.toLowerCase(s.charAt(i++)) != Character.toLowerCase(s.charAt(j--)))
            return false;

    }
    return true;
}
*/