/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.Logable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.util.LinkedList;
import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    125. 验证回文串
        如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个回文串。
        字母和数字都属于字母数字字符。
        给你一个字符串 s，如果它是回文串，返回 true ；否则，返回 false 。
    示例 1：
        输入: "A man, a plan, a canal: Panama"
        输出：true
        解释："amanaplanacanalpanama" 是回文串。
    示例 2：
        输入："race a car"
        输出：false
        解释："raceacar" 不是回文串。
    示例 3：
        输入：s = " "
        输出：true
        解释：在移除非字母数字字符之后，s 是一个空字符串 "" 。
            由于空字符串正着反着读都一样，所以是回文串。
*/
@Slf4j
public class NO125_IsPalindrome_x2 {

    @Test
    public void test() {
        info(isPalindrome("A man, a plan, a canal：Panama"));
    }

    @Logable
    public boolean isPalindrome(String s) {
        return true;
    }

}





/*
public boolean isPalindrome(String s) {
    //解题思路：双指针法，收尾指针对比
    int start = 0;
    int end = s.length() - 1;
    while (start < end) {
        //如果头尾不是字母或数字则往丢弃，看下一个字符
        if(!isLetterOrDigit(s.charAt(start))){
            start++;
            continue;
        }
        if(!isLetterOrDigit(s.charAt(end))){
            end--;
            continue;
        }
        if (toLowerCase(s.charAt(start++)) != toLowerCase(s.charAt(end--)))
            return false;
    }
    return true;
}
*/
